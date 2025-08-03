package com.school_of_company.inform.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.inform.view.InformDetailRoute
import com.school_of_company.inform.view.InformRoute
import com.school_of_company.model.enum.Type


const val InformRoute = "inform"
const val InformDetailRoute = "inform_detail"

fun NavController.navigateToInform(navOptions: NavOptions? = null) {
    this.navigate(InformRoute, navOptions)
}

fun NavController.navigateToInformDetail(
    noticeId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$InformDetailRoute/${noticeId}",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.informScreen(
    onNextClick: (Long) -> Unit
) {
    composable(InformRoute) {
        InformRoute(
            onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.informDetailScreen(
    onBackClick: () -> Unit
) {
    composable(route = "${InformDetailRoute}/{noticeId}") { backStackEntry ->
        val noticeId = backStackEntry.arguments?.getString("noticeId")?.toLongOrNull()
        if (noticeId != null) {
            InformDetailRoute(
                noticeId = noticeId,
                onBackClick = onBackClick
            )
        }
    }
}
