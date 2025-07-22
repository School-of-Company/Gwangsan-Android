package com.school_of_company.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.school_of_company.chat.util.formatChatTime
import com.school_of_company.design_system.R
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.network.socket.model.response.ChatMessage
import com.school_of_company.network.socket.model.response.MessageImage

@Composable
internal fun ChatMessageItem(
    message: ChatMessage,
    modifier: Modifier = Modifier
) {
    GwangSanTheme { colors, typography ->
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            if (!message.isMine) {
                Image(
                    painter = painterResource(id = R.drawable.rectangle),
                    contentDescription = "상대 프로필",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .align(Alignment.Bottom)
                )
                Spacer(modifier = Modifier.width(8.dp))
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(if (message.isMine) Alignment.End else Alignment.Start)
            ) {
                Column(
                    horizontalAlignment = if (message.isMine) Alignment.End else Alignment.Start
                ) {
                    if (message.content.isNotBlank()) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .then(
                                    if (message.isMine) Modifier.background(colors.subYellow500)
                                    else Modifier
                                        .background(colors.white)
                                        .border(1.dp, colors.gray500, RoundedCornerShape(12.dp))
                                )
                                .padding(horizontal = 12.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = message.content,
                                style = typography.label,
                                color = if (message.isMine) colors.white else colors.black
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (message.images?.isNotEmpty() == true) {
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            message.images?.forEach { image ->
                                AsyncImage(
                                    model = image.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Text(
                        text = formatChatTime(message.createdAt),
                        style = typography.caption,
                        color = colors.gray400
                    )
                }
            }

            if (message.isMine) {
                Spacer(modifier = Modifier.width(8.dp))
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatMessageItemPreview() {
    val dummyMessages = listOf(
        ChatMessage(
            messageId = 1L,
            roomId = 100L,
            content = "안녕하세요! 반갑습니다.",
            messageType = "TEXT",
            createdAt = "2025-07-22T10:15:00",
            images = null,
            senderNickname = "홍길동",
            senderId = "user_001",
            checked = true,
            isMine = false
        ),
        ChatMessage(
            messageId = 2L,
            roomId = 100L,
            content = "이 사진 보세요!",
            messageType = "IMAGE",
            createdAt = "2025-07-22T10:16:00",
            images = listOf(
                MessageImage(
                    imageId = 101L,
                    imageUrl = "https://via.placeholder.com/150"
                ),
                MessageImage(
                    imageId = 102L,
                    imageUrl = "https://via.placeholder.com/200"
                )
            ),
            senderNickname = "나",
            senderId = "user_002",
            checked = true,
            isMine = true
        ),
        ChatMessage(
            messageId = 3L,
            roomId = 100L,
            content = "",
            messageType = "IMAGE",
            createdAt = "2025-07-22T10:17:00",
            images = listOf(
                MessageImage(
                    imageId = 103L,
                    imageUrl = "https://via.placeholder.com/180"
                )
            ),
            senderNickname = "홍길동",
            senderId = "user_001",
            checked = true,
            isMine = false
        ),
        ChatMessage(
            messageId = 4L,
            roomId = 100L,
            content = "좋아요~ 다음에 또 봐요!",
            messageType = "TEXT",
            createdAt = "2025-07-22T10:18:00",
            images = null,
            senderNickname = "나",
            senderId = "user_002",
            checked = true,
            isMine = true
        )
    )

    Column(modifier = Modifier.padding(16.dp)) {
        dummyMessages.forEach { message ->
            ChatMessageItem(message = message)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
