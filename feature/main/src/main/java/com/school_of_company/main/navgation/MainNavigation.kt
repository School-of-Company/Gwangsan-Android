package com.school_of_company.main.navgation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.main.view.MainRoute
import com.school_of_company.main.view.MainStartRoute
import com.school_of_company.main.view.NoticeRoute
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type


const val MainRoute = "main_route"
const val MainStartRoute = "main_start_route"
const val NoticeRoute = "notice_route"

fun NavController.navigateToMain(type: String, navOptions: NavOptions? = null) {
    this.navigate("main_route/$type", navOptions)
}

fun NavController.navigateToMainStart(navOptions: NavOptions? = null) {
    this.navigate(MainStartRoute, navOptions)
}

fun NavController.navigateToNoticeScreen(navOptions: NavOptions? = null){
    this.navigate(NoticeRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(
    onBackClick: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    navigationToPost: (Type, Mode) -> Unit,
    onErrorToast: (Throwable?, Int?) -> Unit
) {
    composable("main_route/{type}") { backStackEntry ->
        val typeStr = backStackEntry.arguments?.getString("type") ?: "OBJECT"
        val selectedType = Type.valueOf(typeStr)

        MainRoute(
            onBackClick = onBackClick,
            navigationToPost = { mode ->
                navigationToPost(
                    selectedType,
                    mode
                ) },
            onErrorToast = onErrorToast,
            moDeselectedType = selectedType,
            navigationToDetail = navigateToDetail
        )
    }
}

fun NavGraphBuilder.mainStartScreen(
    navigationToService: () -> Unit,
    navigationToObject: () -> Unit,
    navigationToNotice: () -> Unit
){
    composable(route = MainStartRoute) {
        MainStartRoute(
            navigationToService = navigationToService,
            navigationToObject = navigationToObject,
            navigationToNotice = navigationToNotice
        )
    }
}

fun NavGraphBuilder.noticeScreen(
    onBackClick: () -> Unit,
    navigationToDetail: (Long) -> Unit
){
    composable(route = NoticeRoute){
        NoticeRoute(
            onBackClick = onBackClick,
            navigationToDetail = navigationToDetail
        )
    }
}
