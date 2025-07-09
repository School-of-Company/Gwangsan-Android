package com.school_of_company.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.chat.view.ChatRoom
import com.school_of_company.chat.view.ChatRoomRoute
import com.school_of_company.chat.view.ChatRoute

const val ChatRoute = "chat"
const val ChatRoomRoute = "chat_room"

fun NavController.navigateToChat(navOptions: NavOptions? = null) {
    this.navigate(ChatRoute, navOptions)
}

fun NavController.navigateToChatRoom(navOptions: NavOptions? = null) {
    this.navigate(ChatRoomRoute, navOptions)
}

fun NavGraphBuilder.chatScreen(
    onCloseClick: () -> Unit,
    onChatClick: (ChatRoom) -> Unit
) {
    composable(ChatRoute) {
        ChatRoute(
            onCloseClick = onCloseClick,
            onChatClick = onChatClick
        )
    }
}

fun NavGraphBuilder.chatRoomScreen(
    onBackClick: () -> Unit,
    onSendClick: (String) -> Unit
) {
    composable(ChatRoomRoute) {
        ChatRoomRoute(
            onBackClick = onBackClick,
            onSendClick = onSendClick
        )
    }
}