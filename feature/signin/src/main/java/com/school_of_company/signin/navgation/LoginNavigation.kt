package com.school_of_company.signin.navgation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.signin.view.SignInRoute
import com.school_of_company.signin.view.StartRoute


const val StartRoute = "Start_route"
const val SignInRoute = "Sign_in_route"

fun NavController.navigateToStart(navOptions: NavOptions? = null) {
    this.navigate(StartRoute, navOptions)
}

fun NavGraphBuilder.startScreen(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit,
) {
    composable(route = StartRoute) {
        StartRoute(
            onSignUpClick = onSignUpClick,
            onInputLoginClick = onInputLoginClick
        )
    }
}

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate( SignInRoute, navOptions)
}

fun NavGraphBuilder.signInScreen(
    onBackClick: () -> Unit,
    onMainClick: () -> Unit,
) {
    composable(route = SignInRoute) {
        SignInRoute(
            onBackClick = onBackClick,
            onMainClick = onMainClick,
        )
    }
}

