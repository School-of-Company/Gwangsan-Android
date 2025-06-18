package com.school_of_company.chat.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.chat.component.ChatListItem
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.bottombar.GwangSanNavigationBar
import com.school_of_company.design_system.componet.bottombar.GwangSanNavigationBarItem
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme

// 임시 모델 (API 나오면 model 패키지로 이동)
data class ChatRoom(
    val id: String,
    val name: String,
    val lastMessage: String,
    val unreadCount: Int
)

@Composable
fun ChatScreen(
    chatList: List<ChatRoom>,
    currentTab: Int,
    onTabClick: (Int) -> Unit,
    onCloseClick: () -> Unit,
    onChatClick: (ChatRoom) -> Unit = {},
) {
    GwangSanTheme { colors, typography ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)) {

            Spacer(modifier = Modifier.height(43.dp))

            GwangSanSubTopBar(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
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
                }
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(chatList) { item ->
                    ChatListItem(
                        item = item,
                        modifier = Modifier.GwangSanClickable {
                            onChatClick(item)
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter)
        {
            GwangSanNavigationBar {
                val items = listOf("홈", "게시글", "채팅", "공지", "프로필")
                val icons = listOf(
                    R.drawable.home,
                    R.drawable.copy,
                    R.drawable.chat,
                    R.drawable.horn,
                    R.drawable.person,
                )

                items.forEachIndexed { index, item ->
                    GwangSanNavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = icons[index]),
                                contentDescription = item
                            )
                        },
                        selectedIcon = {
                            Icon(
                                painter = painterResource(id = icons[index]),
                                contentDescription = item,
                                tint = colors.main500
                            )
                        },
                        label = {
                            Text(text = item, style = typography.label)
                        },
                        selected = index == currentTab,
                        onClick = { onTabClick(index) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    val dummyList = listOf(
        ChatRoom("1", "모태환", "안녕하세요 ~.~^^", 1),
        ChatRoom("2", "모태환", "안녕하세요 ~.~^^", 1),
        ChatRoom("3", "모태환", "안녕하세요 ~.~^^", 0),
        ChatRoom("4", "모태환", "안녕하세요 ~.~^^", 0),
    )

    ChatScreen(
        chatList = dummyList,
        currentTab = 2,
        onTabClick = {},
        onCloseClick = {}
    )
}