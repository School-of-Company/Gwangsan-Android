package com.school_of_company.main.navgation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.main.view.MainRoute
import com.school_of_company.main.view.MainStartRoute
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type


const val MainRoute = "main_route"
const val MainStartRoute = "main_start_route"

fun NavController.navigateToMain(type: String, navOptions: NavOptions? = null) {
    this.navigate("main_route/$type", navOptions)
}

fun NavController.navigateToMainStart(navOptions: NavOptions? = null) {
    this.navigate( MainStartRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(
    navigationToPost: (Type, Mode) -> Unit,
    onErrorToast: (Throwable?, Int?) -> Unit
) {
    composable("main_route/{type}") { backStackEntry ->
        val typeStr = backStackEntry.arguments?.getString("type") ?: "OBJECT"
        val selectedType = Type.valueOf(typeStr)

        MainRoute(
            navigationToPost = { mode ->
                navigationToPost(
                    selectedType,
                    mode
                ) },
            onErrorToast = onErrorToast,
            moDeselectedType = selectedType
        )
    }
}

fun NavGraphBuilder.mainStartScreen(
    navigationToService: () -> Unit,
    navigationToObject: () -> Unit,
){
    composable(route = MainStartRoute) {
        MainStartRoute(
            navigationToService = navigationToService,
            navigationToObject = navigationToObject,
        )
    }
}
