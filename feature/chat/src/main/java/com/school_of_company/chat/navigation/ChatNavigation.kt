package com.school_of_company.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.chat.view.ChatRoomIdRoute
import com.school_of_company.chat.view.ChatRoomRoute
import com.school_of_company.chat.view.ChatRoute

const val ChatRoute = "chat"
const val ChatRoomRoute = "chat_room"
const val ChatRoomIdRoute = "chat_room_id"

fun NavController.navigateToChat(navOptions: NavOptions? = null) {
    this.navigate(ChatRoute, navOptions)
}

fun NavController.navigateToChatRoom(
    productId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$ChatRoomRoute/${productId}",
        navOptions = navOptions
    )
}

fun NavController.navigateToChatRoomId(
    roomId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$ChatRoomIdRoute/${roomId}",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.chatScreen(
    onCloseClick: () -> Unit,
    onChatClick: (Long) -> Unit
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
) {
    composable(route = "$ChatRoomRoute/{productId}") { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("productId")?.toLongOrNull()

        if (productId != null) {
            ChatRoomRoute(
                productId = productId,
                onBackClick = onBackClick,
            )
        }
    }
}

fun NavGraphBuilder.chatRoomIdScreen(
    onBackClick: () -> Unit,
) {
    composable(route = "$ChatRoomIdRoute/{roomId}") { backStackEntry ->
        val roomId = backStackEntry.arguments?.getString("roomId")?.toLongOrNull()

        if (roomId != null) {
            ChatRoomIdRoute(
                roomId = roomId,
                onBackClick = onBackClick
            )
        }
    }
}