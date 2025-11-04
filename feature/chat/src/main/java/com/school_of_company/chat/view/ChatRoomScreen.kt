@file:OptIn(ExperimentalMaterial3Api::class)
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.chat.component.ChatMessageItem
import com.school_of_company.chat.component.TradeActionBubble
import com.school_of_company.chat.component.TradeActionBubbleSuccess
import com.school_of_company.chat.component.TradeRequestBottomSheet
import com.school_of_company.chat.util.formatChatTimeToDate
import com.school_of_company.chat.viewmodel.ChatViewModel
import com.school_of_company.chat.viewmodel.uistate.ChatMessageUiState
import com.school_of_company.chat.viewmodel.uistate.ChatTransactionCompleteUiState
import com.school_of_company.chat.viewmodel.uistate.GetLoadTradUiState
import com.school_of_company.chat.viewmodel.uistate.GetMySpecificInformationUiState
import com.school_of_company.chat.viewmodel.uistate.JoinChatUiState
import com.school_of_company.chat.viewmodel.uistate.TradeReservationUiState
import com.school_of_company.content.component.ReviewBottomSheet

import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ChatSendButton
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.DownArrowIcon
import com.school_of_company.design_system.component.icons.addBottomSheetIcon
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.network.socket.manager.ConnectionStatus
import com.school_of_company.ui.previews.GwangsanPreviews
import com.school_of_company.design_system.component.textfield.ChatInputTextField
import com.school_of_company.design_system.component.toast.makeToast
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.model.post.request.TransactionCompleteRequestModel
import com.school_of_company.model.review.request.ReviewRequestModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch


@Composable
internal fun ChatRoomRoute(
    productId: Long,
    initialRoomId: Long? = null,
    onBackClick: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val joinChatUiState by viewModel.joinChatUiState.collectAsStateWithLifecycle()
    val tradeReservationUiState by viewModel.tradeReservationUiState.collectAsStateWithLifecycle()
    val getLoadTradUiState by viewModel.getLoadTradUiState.collectAsStateWithLifecycle()
    val chatMessageUiState by viewModel.chatMessageUiState.collectAsStateWithLifecycle()
    val getMySpecificInformationUiState by viewModel.getMySpecificInformationUiState.collectAsStateWithLifecycle()
    val connectionStatus by viewModel.connectionStatus.collectAsStateWithLifecycle()
    val transactionCompleteUiState by viewModel.transactionCompleteUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val selectedImages = remember { mutableStateListOf<Uri>() }
    val uploadedUris = remember { mutableStateListOf<Uri>() }
    val imageIdMap = remember { mutableStateMapOf<Uri, Long>() }

    val (openReviewBottomSheet, setOpenReviewBottomSheet) = rememberSaveable { mutableStateOf(false) }

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

    LaunchedEffect(productId) {
        viewModel.getMyPostDetail(productId)
    }

    LaunchedEffect(initialRoomId, productId) {
        if (initialRoomId != null) {
            viewModel.joinDirectChatRoom(initialRoomId)
        } else {
            viewModel.joinOrCreateChatRoom(productId)
        }
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.disconnect() }
    }

    LaunchedEffect(tradeReservationUiState) {
        when (tradeReservationUiState) {
            is TradeReservationUiState.Error -> makeToast(context, "예약실패")
            is TradeReservationUiState.Loading -> Unit
            is TradeReservationUiState.Success -> makeToast(context, "예약성공")
            is TradeReservationUiState.Unauthorized -> makeToast(context, "본인을 거래 대상으로 선택할 수 없습니다.")
            is TradeReservationUiState.NotFound -> makeToast(context, "거래실패")
            is TradeReservationUiState.Conflict -> makeToast(context, "이미 예약 완료된 상품입니다.")
            is TradeReservationUiState.BadRequest -> makeToast(context, "진행 중인 상품만 예약할 수 있습니다.")
            else -> Unit
        }
    }

    LaunchedEffect(transactionCompleteUiState) {
        when (transactionCompleteUiState) {
            is ChatTransactionCompleteUiState.Loading -> Unit
            is ChatTransactionCompleteUiState.Error -> makeToast(context, "거래실패")
            is ChatTransactionCompleteUiState.Success -> makeToast(context, "거래성공")
            is ChatTransactionCompleteUiState.Unauthorized -> makeToast(context, "본인을 거래 대상으로 선택할 수 없습니다.")
            is ChatTransactionCompleteUiState.NotFound -> makeToast(context, "거래실패")
            is ChatTransactionCompleteUiState.Conflict -> makeToast(context, "이미 거래 완료된 상품입니다.")
        }
    }

    when (joinChatUiState) {
        is JoinChatUiState.Loading -> LoadingScreen()
        is JoinChatUiState.Error -> {
            ErrorScreen(onRetry = { viewModel.joinOrCreateChatRoom(productId) })
        }
        is JoinChatUiState.Success -> {
            val roomId = (joinChatUiState as JoinChatUiState.Success).data.roomId
            LaunchedEffect(roomId) { viewModel.loadChatMessages(roomId) }

            val (userName, latestMessageTime) = when (val s = getLoadTradUiState) {
                is GetLoadTradUiState.Success -> {
                    val msgs = s.data.message
                    val name = msgs.firstOrNull { !it.isMine }?.senderNickname.orEmpty()
                    val time = msgs.lastOrNull()?.createdAt?.let { formatChatTimeToDate(it) }
                    name to time
                }
                else -> "" to null
            }

            ChatRoomScreen(
                userName = userName,
                lastTime = latestMessageTime,
                getLoadTradUiState = getLoadTradUiState,
                getMySpecificInformationUiState = getMySpecificInformationUiState,
                transactionCompleteUiState = transactionCompleteUiState, // 현재 로직에서는 사용 X (정리 가능)
                connectionStatus = connectionStatus,
                onBackClick = onBackClick,
                onSendClick = { message ->
                    val imageIds = uploadedUris.mapNotNull { imageIdMap[it] }
                    if (message.isNotEmpty() || imageIds.isNotEmpty()) {
                        viewModel.sendMessage(roomId = roomId, content = message, imageIds = imageIds)
                        uploadedUris.clear(); selectedImages.clear(); imageIdMap.clear()
                    }
                },
                onImageAdd = { galleryLauncher.launch("image/*") },
                uploadedUris = uploadedUris.map { it.toString() }.toPersistentList(),
                selectedImages = selectedImages.map { it.toString() }.toPersistentList(),
                onReserveClick = { pid -> viewModel.tradeReservation(postId = pid) },
                onDealClick = { pid, sid ->
                    viewModel.transactionComplete(
                        TransactionCompleteRequestModel(
                            productId = pid,
                            otherMemberId = sid
                        )
                    )
                },
                chatMessageUiState = chatMessageUiState,
                setOpenReviewBottomSheet = setOpenReviewBottomSheet,
                openReviewBottomSheet = openReviewBottomSheet,
                onReviewCallBack = { pid, light, content ->
                    viewModel.reviewPost(
                        body = ReviewRequestModel(
                            productId = pid,
                            content = content,
                            light = light
                        )
                    )
                },
            )
        }
    }
}

@Composable
private fun ChatRoomScreen(
    modifier: Modifier = Modifier,
    userName: String,
    lastTime: String?,
    chatMessageUiState: ChatMessageUiState,
    onReviewCallBack: (Long, Int, String) -> Unit,
    uploadedUris: PersistentList<String>,
    selectedImages: PersistentList<String>,
    getLoadTradUiState: GetLoadTradUiState,
    getMySpecificInformationUiState: GetMySpecificInformationUiState,
    setOpenReviewBottomSheet: (Boolean) -> Unit,
    openReviewBottomSheet: Boolean,
    transactionCompleteUiState: ChatTransactionCompleteUiState, // 현재 판단에는 미사용
    connectionStatus: ConnectionStatus,
    onBackClick: () -> Unit,
    onSendClick: (String) -> Unit,
    onImageAdd: () -> Unit,
    onReserveClick: (Long) -> Unit,
    onDealClick: (Long, Long) -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var openTradeBottomSheet by rememberSaveable { mutableStateOf(false) }
    var sheetProduct by remember { mutableStateOf<com.school_of_company.chat.ui.model.TradeProductUi?>(null) }
    var sheetMessage by remember { mutableStateOf<com.school_of_company.chat.ui.model.ChatMessageUi?>(null) }

    val context = LocalContext.current

    GwangSanTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .imePadding()
                .background(colors.white)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            val modeStr = ((getMySpecificInformationUiState as? GetMySpecificInformationUiState.Success)
                ?.data
                ?.mode)
                ?.toString()
            val isGiver = modeStr.equals("giver", ignoreCase = true)

            GwangSanSubTopBar(
                modifier = Modifier.height(50.dp),
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                endIcon = {
                    if (isGiver) {
                        addBottomSheetIcon(
                            modifier = Modifier.GwangSanClickable {
                                val success = getLoadTradUiState as? GetLoadTradUiState.Success
                                val product = success?.data?.product
                                val targetMsg = success?.data?.message
                                    ?.lastOrNull { !it.isMine } ?: success?.data?.message?.lastOrNull()
                                if (product != null && targetMsg != null) {
                                    sheetProduct = product
                                    sheetMessage = targetMsg
                                    openTradeBottomSheet = true
                                } else {
                                    Log.w("ChatRoom", "No product/message to open TradeRequestBottomSheet")
                                }
                            }
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))

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
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = userName.ifBlank { "상대방" },
                    style = typography.titleSmall,
                    color = colors.black
                )

                Spacer(modifier = Modifier.height(34.dp))
                val statusText = when (connectionStatus) {
                    ConnectionStatus.CONNECTED -> lastTime
                    ConnectionStatus.CONNECTING -> "연결 중..."
                    ConnectionStatus.DISCONNECTED -> "오프라인"
                    ConnectionStatus.ERROR -> "에러.."
                }
                statusText?.let {
                    Text(text = it, style = typography.label, color = colors.gray400, textAlign = TextAlign.Center)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 1) 최초 로드(기준) + 상품
            val baseData = (getLoadTradUiState as? GetLoadTradUiState.Success)?.data
            val baseMessages = baseData?.message.orEmpty()
            val tradeProduct = baseData?.product

            // 2) 실시간 메시지 (ChatMessageItem만)
            val liveAll = (chatMessageUiState as? ChatMessageUiState.Success)?.data.orEmpty()

            // 3) 기준 메시지 id 집합
            val baseIds = remember(baseMessages) { baseMessages.map { it.messageId }.toSet() }

            // 4) 최종 머지: 기준 + (기준에 없는) 실시간
            val mergedMessages = remember(baseMessages, liveAll) {
                val liveOnly = liveAll.filter { it.messageId !in baseIds }
                baseMessages + liveOnly
            }

            // 5) 버블은 '첫 기준 메시지' 위치에서만 1회 렌더
            val firstBubbleIndex = remember(mergedMessages, baseIds) {
                mergedMessages.indexOfFirst { it.messageId in baseIds }
            }

            // 거래 완료 여부는 product에서 판단
            val isProductCompleted = tradeProduct?.isCompleted == true
            // 만약 서버/모델 필드명이 isCompated 라면 아래로 바꿔 사용:
            // val isProductCompleted = tradeProduct?.isCompated == true

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                itemsIndexed(items = mergedMessages, key = { _, m -> m.messageId }) { index, message ->
                    val showBubbleOnce = tradeProduct != null && index == firstBubbleIndex && firstBubbleIndex >= 0
                    if (showBubbleOnce) {
                        if (isProductCompleted) {
                            TradeActionBubbleSuccess(
                                data = tradeProduct!!,
                                onReviewClick = {
                                    sheetProduct = tradeProduct
                                    setOpenReviewBottomSheet(true)
                                },
                                message = message,
                            )
                        } else {
                            val productId = tradeProduct!!.id
                            val senderId = message.senderId
                            TradeActionBubble(
                                data = tradeProduct,
                                message = message,
                                onReserveClick = { onReserveClick(productId) },
                                onDealClick = { onDealClick(productId, senderId) }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    ChatMessageItem(message = message)
                }
            }

            // 6) 스크롤 하단 고정
            LaunchedEffect(mergedMessages.size) {
                if (mergedMessages.isNotEmpty()) {
                    coroutineScope.launch { listState.animateScrollToItem(mergedMessages.size - 1) }
                }
            }

            // 7) 로딩/에러 상태 표시 (리스트 비었을 때만 센터 표시)
            when {
                (chatMessageUiState is ChatMessageUiState.Loading ||
                        getLoadTradUiState is GetLoadTradUiState.Loading) && mergedMessages.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) { Text("메시지 로딩 중...", style = typography.body3) }
                }

                chatMessageUiState is ChatMessageUiState.Error && mergedMessages.isEmpty() -> {
                    val ex = (chatMessageUiState as ChatMessageUiState.Error).exception
                    Log.e("ChatRoomScreen", "UIState Error(chat): ${ex.message}", ex)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) { Text("전송 실패..", style = typography.body3, color = colors.error) }
                }

                getLoadTradUiState is GetLoadTradUiState.Error && mergedMessages.isEmpty() -> {
                    val ex = (getLoadTradUiState as GetLoadTradUiState.Error).exception
                    Log.e("ChatRoomScreen", "UIState Error(load): ${ex.message}", ex)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) { Text("전송 실패..", style = typography.body3, color = colors.error) }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            ImagePreviewRow(
                uploadedUris = uploadedUris,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
            ) {
                ChatInputTextField(
                    value = text,
                    onValueChange = { text = it },
                    onImageClick = { onImageAdd() },
                    enabled = connectionStatus == ConnectionStatus.CONNECTED,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                val canSendMessage = (text.isNotBlank() || uploadedUris.isNotEmpty()) &&
                        selectedImages.size == uploadedUris.size &&
                        connectionStatus == ConnectionStatus.CONNECTED
                ChatSendButton(
                    onClick = { if (canSendMessage) { onSendClick(text.trim()); text = "" } },
                    enabled = canSendMessage
                )
            }
        }

        if (openTradeBottomSheet && sheetProduct != null && sheetMessage != null) {
            TradeRequestBottomSheet(
                onDismiss = { openTradeBottomSheet = false },
                onRequestClick = { pid, sid ->
                    onDealClick(pid, sid)
                    openTradeBottomSheet = false
                },
                data = sheetProduct!!,
                message = sheetMessage!!,
                onCancelClick = { openTradeBottomSheet = false },
            )
        }

        if (openReviewBottomSheet) {
            Dialog(onDismissRequest = { setOpenReviewBottomSheet(false) }) {
                ReviewBottomSheet(
                    onDismiss = { setOpenReviewBottomSheet(false) },
                    onSubmit = { reportType, reportContent ->
                        val productId = sheetProduct?.id
                        if (productId != null) {
                            onReviewCallBack(productId, reportType.toInt(), reportContent)
                        } else {
                            Log.w("ChatRoom", "Review submit clicked without productId")
                            makeToast(context, "상품 정보를 찾을 수 없어요.")
                        }
                        setOpenReviewBottomSheet(false)
                    }
                )
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    GwangSanTheme { colors, typography ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.white),
            contentAlignment = Alignment.Center
        ) { Text("채팅방 준비 중...", style = typography.body3) }
    }
}

@Composable
private fun ErrorScreen(onRetry: () -> Unit) {
    GwangSanTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("채팅방 입장 실패", style = typography.titleSmall, color = colors.error)
            Spacer(modifier = Modifier.height(16.dp))
            ChatSendButton(onClick = onRetry)
        }
    }
}

@Composable
private fun ImagePreviewRow(
    uploadedUris: PersistentList<String>,
    modifier: Modifier = Modifier
) {
    if (uploadedUris.isEmpty()) return
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        uploadedUris.forEach { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Uploaded Image Preview",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

@GwangsanPreviews
@Composable
private fun ChatRoomScreenPreview() {}
