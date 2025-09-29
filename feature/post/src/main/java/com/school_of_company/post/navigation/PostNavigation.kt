package com.school_of_company.post.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.view.PostRoute

const val PostRoute = "post"

fun NavController.navigateToPost(
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = PostRoute,
        navOptions = navOptions
    )
}

fun NavController.navigateToPostEdit(
    postId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$PostRoute/$postId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.postScreen(
    onBackClick: () -> Unit,
    onCreateComplete: () -> Unit,
    onEditComplete: () -> Unit
) {
    composable(route = PostRoute) {
        PostRoute(
            onBackClick = onBackClick,
            onCreateComplete = onCreateComplete,
            onEditComplete = onEditComplete
        )
    }

    composable(
        route = "$PostRoute/{postId}",
        arguments = listOf(
            navArgument("postId") { type = NavType.LongType }
        )
    ) { backStackEntry ->
        val postId = backStackEntry.arguments?.getLong("postId") ?: 0L

        PostRoute(
            editPostId = postId,
            onBackClick = onBackClick,
            onEditComplete = onEditComplete,
            onCreateComplete = onCreateComplete
        )
    }

}