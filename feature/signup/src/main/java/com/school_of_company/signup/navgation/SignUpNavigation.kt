package com.school_of_company.signup.navgation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.signup.view.*


const val SignUpNameRoute = "signup_name"
const val SignUpPasswordRoute = "signup_password"
const val SignUpPhoneRoute = "signup_phone"
const val SignUpNeighborhoodRoute = "signup_neighborhood"
const val SignUpIntroduceRoute = "signup_introduce"
const val SignUpRecommenderRoute = "signup_recommender"
const val SignUpFinishRoute = "signup_finish"


fun NavController.navigateToSignUpStart(navOptions: NavOptions? = null) {
    this.navigate(SignUpNameRoute, navOptions)
}

fun NavController.navigateToSignUpPassword(navOptions: NavOptions? = null) {
    this.navigate(SignUpPasswordRoute)
}

fun NavController.navigateToSignUpPhone(navOptions: NavOptions? = null) {
    this.navigate(SignUpPhoneRoute)
}

fun NavController.navigateToSignUpNeighborhood(navOptions: NavOptions? = null) {
    this.navigate(SignUpNeighborhoodRoute)
}

fun NavController.navigateToSignUpIntroduce(navOptions: NavOptions? = null) {
    this.navigate(SignUpIntroduceRoute)
}

fun NavController.navigateToSignUpRecommender(navOptions: NavOptions? = null) {
    this.navigate(SignUpRecommenderRoute)
}

fun NavController.navigateToSignUpFinish(navOptions: NavOptions? = null) {
    this.navigate(SignUpFinishRoute)
}


fun NavGraphBuilder.signUpNameScreen(
    onBackClick: () -> Unit,
    onPasswordClick: ()-> Unit,
) {
    composable(route = SignUpNameRoute) {
        NameSignupRoute(
            onBackClick = onBackClick,
            onPasswordClick = onPasswordClick
        )
    }
}

fun NavGraphBuilder.signUpPasswordScreen(
    onBackClick: () -> Unit,
    onCerTinSignUpClick: () -> Unit
) {
    composable(route = SignUpPasswordRoute) {
        PasswordSignupRoute(
            onBackClick = onBackClick,
            onCerTinSignUpClick = onCerTinSignUpClick
        )
    }
}

fun NavGraphBuilder.signUpPhoneScreen(
    onBackClick: () -> Unit,
    onNeighborhoodClick: () -> Unit
){
    composable(route = SignUpPhoneRoute) {
        CertInSignUpRoute(
            onBackClick = onBackClick,
            onNeighborhoodClick = onNeighborhoodClick
        )
    }
}

fun NavGraphBuilder.signUpNeighborhoodScreen(
    onBackClick: () -> Unit,
    onIntroduceClick: () -> Unit
) {
    composable(route = SignUpNeighborhoodRoute) {
        NeighborhoodSignupRoute(
            onBackClick = onBackClick,
            onIntroduceClick = onIntroduceClick
        )
    }
}

fun NavGraphBuilder.signUpIntroduceScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    composable(route = SignUpIntroduceRoute) {
        IntroduceRoute(
            onBackClick = onBackClick,
            onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.signUpRecommenderScreen(
    onBackClick: () -> Unit,
    onRecommenderClick: () -> Unit
) {
    composable(route = SignUpRecommenderRoute) {
        ReCommDerInputRoute(
            onBackClick = onBackClick,
            onRecommenderClick = onRecommenderClick
        )
    }
}

fun NavGraphBuilder.signUpFinishScreen(
    onClickGoToLogin: () -> Unit
) {
    composable(route = SignUpFinishRoute) {
        FinishRoute(
            onClickGoToLogin = onClickGoToLogin
        )
    }
}