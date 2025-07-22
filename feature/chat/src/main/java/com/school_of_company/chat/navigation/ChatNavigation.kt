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

fun NavController.navigateToChatRoom(
    productId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$ChatRoomRoute/${productId}",
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