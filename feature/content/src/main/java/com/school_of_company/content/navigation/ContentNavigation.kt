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

fun NavController.navigateToReadMore(navOptions: NavOptions? = null) {
    this.navigate(ReadMoreRoute, navOptions)
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
    onMyProfileClick: () -> Unit,
    onChatClick: () -> Unit,
    onReviewClick: (Int, String) -> Unit,
    onReportClick: (String, String) -> Unit
) {
    composable(ReadMoreRoute) {
        ReadMoreRoute(
            onBackClick = onBackClick,
            onMyProfileClick = onMyProfileClick,
            onChatClick = onChatClick,
            onReviewClick = onReviewClick,
            onReportClick = onReportClick
        )
    }
}
