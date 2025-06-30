package com.school_of_company.chat.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.chat.component.ChatMessageItem
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.button.ChatSendButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.yourpackage.design_system.component.textField.ChatInputTextField

internal data class ChatMessage(
    val id: String,
    val text: String,
    val isMine: Boolean
)

@Composable
private fun ChatRoomScreen(
    modifier: Modifier = Modifier,
    userName: String,
    lastSeenTime: String,
    onBackClick: () -> Unit,
    onSendClick: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    val messages = remember {
        listOf(
            ChatMessage("1", "안녕하세요 상품 보고 연락 드려요", true),
            ChatMessage("2", "안녕하세요!!", false),
            ChatMessage("3", "지금 거래 가능하나요", true),
            ChatMessage("4", "네 거래 가능합니다", false),
        )
    }

    GwangSanTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            GwangSanTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                betweenText = "뒤로"
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
                    text = userName,
                    style = typography.titleSmall
                )

                Spacer(modifier = Modifier.height(34.dp))

                Text(
                    text = lastSeenTime,
                    style = typography.label,
                    color = colors.gray400,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(
                    items = messages,
                    key = { it.id }
                ) { message ->
                    ChatMessageItem(message = message)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
            ) {
                ChatInputTextField(
                    value = text,
                    onValueChange = { text = it },
                    onImageClick = { /* 이미지 첨부 */ },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                ChatSendButton(
                    onClick = {
                    onSendClick(text)
                    text = ""
                })
            }
        }
    }
}

@Preview
@Composable
private fun ChatRoomScreenPreview() {
    ChatRoomScreen(
        userName = "모태환",
        lastSeenTime = "오전 10:51",
        onBackClick = {},
        onSendClick = {}
    )
}
