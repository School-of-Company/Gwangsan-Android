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

fun NavController.navigateToInformDetail(navOptions: NavOptions? = null) {
    this.navigate(InformDetailRoute, navOptions)
}

fun NavGraphBuilder.informScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    composable(InformRoute) {
        InformRoute(
            onBackClick = onBackClick,
            onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.informDetailScreen(
    onBackClick: () -> Unit
) {
    composable(InformDetailRoute) {
        InformDetailRoute(
            onBackClick = onBackClick
        )
    }
}
