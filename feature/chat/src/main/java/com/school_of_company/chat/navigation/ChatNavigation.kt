package com.school_of_company.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.school_of_company.chat.view.ChatRoomRoute
import com.school_of_company.chat.view.ChatRoute

const val ChatRoute = "chat"
const val ChatRoomRoute = "chat_room"
private const val ChatRoomProductIdArg = "productId"
private const val ChatRoomRoomIdArg = "roomId"
private const val ChatRoomDestination =
    "$ChatRoomRoute/{$ChatRoomProductIdArg}?$ChatRoomRoomIdArg={$ChatRoomRoomIdArg}"

fun NavController.navigateToChat(navOptions: NavOptions? = null) {
    this.navigate(ChatRoute, navOptions)
}

fun NavController.navigateToChatRoom(
    productId: Long,
    roomId: Long? = null,
    navOptions: NavOptions? = null
) {
    val baseRoute = "$ChatRoomRoute/$productId"
    val route = if (roomId != null) {
        "$baseRoute?$ChatRoomRoomIdArg=$roomId"
    } else {
        baseRoute
    }
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.chatScreen(
    onCloseClick: () -> Unit,
    onChatClick: (productId: Long, roomId: Long) -> Unit
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
    composable(
        route = ChatRoomDestination,
        arguments = listOf(
            navArgument(ChatRoomProductIdArg) { type = NavType.LongType },
            navArgument(ChatRoomRoomIdArg) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    ) { backStackEntry ->
        val arguments = backStackEntry.arguments
        val productId = arguments?.getLong(ChatRoomProductIdArg) ?: return@composable
        val roomIdValue = arguments.getLong(ChatRoomRoomIdArg)
        val roomId = roomIdValue.takeIf { it != -1L }

        ChatRoomRoute(
            productId = productId,
            initialRoomId = roomId,
            onBackClick = onBackClick,
        )
    }
}
