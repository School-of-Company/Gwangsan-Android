package com.school_of_company.content.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.content.view.ContentRoute
import com.school_of_company.content.view.ReadMoreRoute

const val ContentRoute = "content"
const val ReadMoreRoute = "read_more"

fun NavController.navigateToContent(navOptions: NavOptions? = null) {
    this.navigate(ContentRoute, navOptions)
}

fun NavController.navigateToReadMore(
    postId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$ReadMoreRoute/${postId}",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.contentScreen(
    onMyProfileClick: () -> Unit,
    onItemClick: () -> Unit
) {
    composable(ContentRoute) {
        ContentRoute(
            onMyProfileClick = onMyProfileClick,
            onItemClick = onItemClick
        )
    }
}

fun NavGraphBuilder.readMoreScreen(
    onBackClick: () -> Unit,
    onOtherProfileClick: (Long) -> Unit,
    onChatClick: () -> Unit,
    onReviewClick: (Int, String) -> Unit,
    onReportClick: (String, String) -> Unit,
    onEditClick: (Long, String, String) -> Unit,
) {
    composable(route = "${ReadMoreRoute}/{postId}") { backStackEntry ->
        val postId = backStackEntry.arguments?.getString("postId")?.toLongOrNull()

        if (postId != null) {
            ReadMoreRoute(
                onBackClick = onBackClick,
                onOtherProfileClick = onOtherProfileClick,
                onChatClick = onChatClick,
                onReviewClick = onReviewClick,
                onReportClick = onReportClick,
                postId = postId,
                onEditClick = onEditClick
            )
        }
    }
}
