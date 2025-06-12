package com.school_of_company.signin.navgation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.signin.view.LoginRoute
import com.school_of_company.signin.view.SignInRoute


const val loginRoute = "login_route"
const val SignInRoute = "Sign_in_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loginRoute, navOptions)
}

fun NavGraphBuilder.startScreen(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit,
) {
    composable(route = loginRoute) {
        LoginRoute(
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
    onRePasswordClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = SignInRoute) {
        SignInRoute(
            onBackClick = onBackClick,
            onMainClick = onMainClick,
            onRePasswordClick = onRePasswordClick,
            onErrorToast = onErrorToast
        )
    }
}

