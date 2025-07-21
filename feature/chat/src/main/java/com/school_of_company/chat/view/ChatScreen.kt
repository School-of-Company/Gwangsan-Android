package com.school_of_company.chat.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.school_of_company.chat.component.ChatListItem
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.ui.previews.GwangsanPreviews

// 임시 모델 (API 나오면 model 패키지로 이동)
data class ChatRoom(
    val id: String,
    val name: String,
    val lastMessage: String,
    val unreadCount: Int
)

@Composable
internal fun ChatRoute(
    onCloseClick: () -> Unit,
    onChatClick: (ChatRoom) -> Unit
) {
    ChatScreen(
        chatList = listOf(
            ChatRoom(
                id = "string_id",
                name = "김치라",
                lastMessage = "안녕하세욤",
                unreadCount = 1
            ),
            ChatRoom(
                id = "string_id",
                name = "김치라",
                lastMessage = "안녕하세욤",
                unreadCount = 1
            ),ChatRoom(
                id = "string_id",
                name = "김치라",
                lastMessage = "안녕하세욤",
                unreadCount = 1
            ),ChatRoom(
                id = "string_id",
                name = "김치라",
                lastMessage = "안녕하세욤",
                unreadCount = 1
            ),ChatRoom(
                id = "string_id",
                name = "김치라",
                lastMessage = "안녕하세욤",
                unreadCount = 1
            )
        ),
        onCloseClick = onCloseClick,
        onChatClick = onChatClick
    )
}

@Composable
private fun ChatScreen(
    chatList: List<ChatRoom>,
    onCloseClick: () -> Unit,
    onChatClick: (ChatRoom) -> Unit = {},
) {
    GwangSanTheme { colors, _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(bottom = 56.dp)
        ) {

            Spacer(modifier = Modifier.height(43.dp))

            GwangSanSubTopBar(
                startIcon = { Spacer(modifier = Modifier.size(24.dp)) },
                betweenText = "채팅",
                endIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = "닫기",
                        modifier = Modifier
                            .size(24.dp)
                            .GwangSanClickable { onCloseClick() }
                    )
                },
                modifier = Modifier.padding(all = 24.dp),
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = chatList,
                    key = { it.id }
                ) { item ->
                    ChatListItem(
                        item = item,
                        modifier = Modifier.GwangSanClickable { onChatClick(item) }
                    )
                }
            }
        }
    }
}

@GwangsanPreviews
@Composable
private fun ChatScreenPreview() {
    val dummyList = listOf(
        ChatRoom("1", "모태환", "안녕하세요 ~.~^^", 1),
        ChatRoom("2", "모태환", "안녕하세요 ~.~^^", 1),
        ChatRoom("3", "모태환", "안녕하세요 ~.~^^", 0),
        ChatRoom("4", "모태환", "안녕하세요 ~.~^^", 0),
        ChatRoom("5", "모태환", "안녕하세요 ~.~^^", 0),
        ChatRoom("6", "모태환", "안녕하세요 ~.~^^", 0),
        ChatRoom("7", "모태환", "안녕하세요 ~.~^^", 0),
        ChatRoom("8", "모태환", "안녕하세요 ~.~^^", 0),
    )

    ChatScreen(
        chatList = dummyList,
        onCloseClick = {}
    )
}