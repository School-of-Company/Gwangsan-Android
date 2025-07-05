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
    type: Type,
    mode: Mode,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$PostRoute/${type.name}/${mode.name}",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.postScreen(
    onBackClick: () -> Unit,
    onComplete: () -> Unit
) {
   composable(
       route = "$PostRoute/{type}/{mode}",
       arguments = listOf(
           navArgument("type") { type = NavType.StringType },
           navArgument("mode") { type = NavType.StringType }
       )
   ) { backStackEntry ->
       val typeStr = backStackEntry.arguments?.getString("type") ?: Type.SERVICE.name
       val modeStr = backStackEntry.arguments?.getString("mode") ?: Mode.GIVER.name
       val type = Type.valueOf(typeStr)
       val mode = Mode.valueOf(modeStr)

       PostRoute(
           type = type,
           mode = mode,
           onBackClick = onBackClick,
           onComplete = onComplete
       )
   }
}