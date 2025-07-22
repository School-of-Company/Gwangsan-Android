package com.school_of_company.chat.view

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.chat.component.ChatMessageItem
import com.school_of_company.chat.util.formatChatTimeToDate
import com.school_of_company.chat.viewmodel.ChatViewModel
import com.school_of_company.chat.viewmodel.uistate.ChatMessageUiState
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ChatSendButton
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.DownArrowIcon
import com.school_of_company.design_system.component.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.network.socket.manager.ConnectionStatus
import com.yourpackage.design_system.component.textField.ChatInputTextField
import kotlinx.coroutines.launch

@Composable
internal fun ChatRoomIdRoute(
    roomId: Long,
    onBackClick: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val chatMessageUiState by viewModel.chatMessageUiState.collectAsStateWithLifecycle()
    val connectionStatus by viewModel.connectionStatus.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val selectedImages = remember { mutableStateListOf<Uri>() }
    val uploadedUris = remember { mutableStateListOf<Uri>() }
    val imageIdMap = remember { mutableStateMapOf<Uri, Long>() }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> selectedImages.addAll(uris) }
    )

    LaunchedEffect(selectedImages.toList()) {
        selectedImages.forEach { uri ->
            if (!uploadedUris.contains(uri)) {
                try {
                    val imageId = viewModel.imageUpLoad(context, uri)
                    imageIdMap[uri] = imageId
                    uploadedUris.add(uri)
                } catch (e: Exception) {
                    Log.e("ChatRoomRoute", "Image upload failed", e)
                }
            }
        }
    }

    LaunchedEffect(roomId) {
        viewModel.joinDirectChatRoom(roomId)
    }

    when (chatMessageUiState) {
        is ChatMessageUiState.Loading -> {
            LoadingScreen()
        }

        is ChatMessageUiState.Success -> {
            val messages = (chatMessageUiState as ChatMessageUiState.Success).data
            val userName = messages.firstOrNull { !it.isMine }?.senderNickname.orEmpty()
            val latestMessageTime = messages.lastOrNull()?.createdAt?.let { formatChatTimeToDate(it) }

            ChatRoomScreen(
                userName = userName,
                lastTime = latestMessageTime,
                chatMessageUiState = chatMessageUiState,
                connectionStatus = connectionStatus,
                onBackClick = onBackClick,
                onSendClick = { message ->
                    val imageIds = uploadedUris.mapNotNull { imageIdMap[it] }
                    if (message.isNotEmpty() || imageIds.isNotEmpty()) {
                        viewModel.sendMessage(
                            roomId = roomId,
                            content = message,
                            imageIds = imageIds
                        )
                        uploadedUris.clear()
                        selectedImages.clear()
                        imageIdMap.clear()
                    }
                },
                onImageAdd = { galleryLauncher.launch("image/*") },
                uploadedUris = uploadedUris,
                selectedImages = selectedImages
            )
        }

        is ChatMessageUiState.Error -> {
            ErrorScreen(
                error = (chatMessageUiState as ChatMessageUiState.Error).exception,
                onRetry = { viewModel.loadChatMessages(roomId) }
            )
        }
    }
}

@Composable
private fun ChatRoomScreen(
    modifier: Modifier = Modifier,
    userName: String,
    lastTime: String?,
    uploadedUris: List<Uri>,
    selectedImages: List<Uri>,
    chatMessageUiState: ChatMessageUiState,
    connectionStatus: ConnectionStatus,
    onBackClick: () -> Unit,
    onSendClick: (String) -> Unit,
    onImageAdd: () -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    GwangSanTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .imePadding()
                .background(colors.white)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(60.dp))

            GwangSanTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                betweenText = when (connectionStatus) {
                    ConnectionStatus.CONNECTED -> "뒤로"
                    ConnectionStatus.CONNECTING -> "연결 중..."
                    ConnectionStatus.DISCONNECTED -> "연결 끊김"
                    ConnectionStatus.ERROR -> "에러.."
                }
            )

            Spacer(Modifier.height(12.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rectangle),
                    contentDescription = "상대 프로필",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = userName,
                    style = typography.titleSmall,
                    color = colors.black
                )

                Spacer(Modifier.height(34.dp))

                lastTime?.let {
                    Text(
                        text = it,
                        style = typography.label,
                        color = colors.gray400,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            when (chatMessageUiState) {
                is ChatMessageUiState.Loading -> Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("메시지 로딩 중...", style = typography.body3)
                }

                is ChatMessageUiState.Success -> {
                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    ) {
                        items(
                            items = chatMessageUiState.data,
                            key = { it.messageId }
                        ) { message ->
                            ChatMessageItem(message = message)
                        }
                    }

                    LaunchedEffect(chatMessageUiState.data.size) {
                        if (chatMessageUiState.data.isNotEmpty()) {
                            coroutineScope.launch {
                                listState.animateScrollToItem(chatMessageUiState.data.size - 1)
                            }
                        }
                    }
                }

                is ChatMessageUiState.Error -> Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "메시지 로딩 실패..",
                        style = typography.body3,
                        color = colors.error
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            ImagePreviewRow(
                uploadedUris = uploadedUris,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            ) {
                ChatInputTextField(
                    value = text,
                    onValueChange = { text = it },
                    onImageClick = onImageAdd,
                    enabled = connectionStatus == ConnectionStatus.CONNECTED,
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(8.dp))

                val canSend = (text.isNotBlank() || uploadedUris.isNotEmpty()) &&
                        selectedImages.size == uploadedUris.size &&
                        connectionStatus == ConnectionStatus.CONNECTED

                ChatSendButton(
                    onClick = {
                        if (canSend) {
                            onSendClick(text.trim())
                            text = ""
                        }
                    },
                    enabled = canSend
                )
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    GwangSanTheme { colors, typography ->
        Box(
            Modifier.fillMaxSize().background(colors.white),
            contentAlignment = Alignment.Center
        ) {
            Text("채팅방 준비 중...", style = typography.body3)
        }
    }
}

@Composable
private fun ErrorScreen(
    error: Throwable,
    onRetry: () -> Unit
) {
    GwangSanTheme { colors, typography ->
        Column(
            Modifier.fillMaxSize().background(colors.white).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("채팅방 입장 실패", style = typography.titleSmall, color = colors.error)

            Spacer(Modifier.height(8.dp))

            Text(error.message ?: "알 수 없는 오류", style = typography.body3, color = colors.gray400)

            Spacer(Modifier.height(16.dp))

            ChatSendButton(onClick = onRetry)
        }
    }
}

@Composable
private fun ImagePreviewRow(
    uploadedUris: List<Uri>,
    modifier: Modifier = Modifier
) {
    if (uploadedUris.isEmpty()) return

    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        uploadedUris.forEach { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Uploaded Image Preview",
                modifier = Modifier.size(64.dp).clip(RoundedCornerShape(8.dp))
            )
        }
    }
}
