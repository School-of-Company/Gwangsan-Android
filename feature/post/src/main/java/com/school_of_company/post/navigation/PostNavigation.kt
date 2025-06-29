package com.school_of_company.post.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.view.PostFinalRoute
import com.school_of_company.post.view.PostInputRoute
import com.school_of_company.post.view.PostWriteRoute

const val PostWriteRoute = "post_write"
const val PostInputRoute = "post_input"
const val PostFinalRoute = "post_final"

fun NavController.navigateToPostWrite(
    type: Type,
    mode: Mode,
    navOptions: NavOptions? = null
) {
    this.navigate("$PostWriteRoute/${type.name}/${mode.name}", navOptions)
}

fun NavController.navigateToPostInput(
    type: Type,
    mode: Mode,
    navOptions: NavOptions? = null
) {
    this.navigate("$PostInputRoute/${type.name}/${mode.name}", navOptions)
}

fun NavController.navigateToPostFinal(
    type: Type,
    mode: Mode,
    navOptions: NavOptions? = null
) {
    this.navigate("$PostFinalRoute/${type.name}/${mode.name}", navOptions)
}

fun NavGraphBuilder.postWriteScreen(
    onBackClick: () -> Unit,
    onNextClick: (String, String) -> Unit
) {
    composable(
        route = "$PostWriteRoute/{type}/{mode}",
        arguments = listOf(
            navArgument("type") { type = NavType.StringType },
            navArgument("mode") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val typeStr = backStackEntry.arguments?.getString("type") ?: Type.SERVICE.name
        val modeStr = backStackEntry.arguments?.getString("mode") ?: Mode.GIVER.name
        val type = Type.valueOf(typeStr)
        val mode = Mode.valueOf(modeStr)

        PostWriteRoute(
            type = type,
            mode = mode,
            onBackClick = onBackClick,
            onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.postInputScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    composable(
        route = "$PostInputRoute/{type}/{mode}",
        arguments = listOf(
            navArgument("type") { type = NavType.StringType },
            navArgument("mode") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val typeStr = backStackEntry.arguments?.getString("type") ?: Type.SERVICE.name
        val modeStr = backStackEntry.arguments?.getString("mode") ?: Mode.GIVER.name
        val type = Type.valueOf(typeStr)
        val mode = Mode.valueOf(modeStr)

        PostInputRoute(
            type = type,
            mode = mode,
            onBackClick = onBackClick,
            onNextClick = onNextClick,
            onCloseClick = onCloseClick
        )
    }
}

fun NavGraphBuilder.postFinalScreen(
    subject: String,
    content: String,
    price: String,
    onEditClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    composable(
        route = "$PostFinalRoute/{type}/{mode}",
        arguments = listOf(
            navArgument("type") { type = NavType.StringType },
            navArgument("mode") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val typeStr = backStackEntry.arguments?.getString("type") ?: Type.SERVICE.name
        val modeStr = backStackEntry.arguments?.getString("mode") ?: Mode.GIVER.name
        val type = Type.valueOf(typeStr)
        val mode = Mode.valueOf(modeStr)

       PostFinalRoute(
            type = type,
            mode = mode,
            onEditClick = onEditClick,
            onSubmitClick = onSubmitClick,
            onBackClick = onBackClick,
            onCloseClick = onCloseClick
        )
    }
}