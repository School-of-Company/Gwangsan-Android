package com.school_of_company.gwangsan_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.school_of_company.gwangsan_android.ui.GwangSanAppState
import com.school_of_company.signin.navigation.StartRoute
import com.school_of_company.signin.navigation.navigateToSignIn
import com.school_of_company.signin.navigation.signInScreen
import com.school_of_company.signin.navigation.startScreen
import com.school_of_company.signup.navigation.navigateToSignUpFinish
import com.school_of_company.signup.navigation.navigateToSignUpIntroduce
import com.school_of_company.signup.navigation.navigateToSignUpNeighborhood
import com.school_of_company.signup.navigation.navigateToSignUpPassword
import com.school_of_company.signup.navigation.navigateToSignUpPhone
import com.school_of_company.signup.navigation.navigateToSignUpRecommender
import com.school_of_company.signup.navigation.navigateToSignUpStart
import com.school_of_company.signup.navigation.signUpFinishScreen
import com.school_of_company.signup.navigation.signUpIntroduceScreen
import com.school_of_company.signup.navigation.signUpNameScreen
import com.school_of_company.signup.navigation.signUpNeighborhoodScreen
import com.school_of_company.signup.navigation.signUpPasswordScreen
import com.school_of_company.signup.navigation.signUpPhoneScreen
import com.school_of_company.signup.navigation.signUpRecommenderScreen


@Composable
fun GwangsanNavHost(
    modifier: Modifier = Modifier,
    appState: GwangSanAppState,
    startDestination: String = StartRoute
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        startScreen(
            onSignUpClick = { navController.navigateToSignUpStart() },
            onInputLoginClick = { navController.navigateToSignIn() }
        )

        signInScreen(
            onBackClick = { navController.popBackStack() },
            onMainClick = { navController.popBackStack() }
        )

        signUpNameScreen(
            onBackClick = { navController.popBackStack() },
            onPasswordClick = { navController.navigateToSignUpPassword() }

        )

        signUpPasswordScreen(
            onBackClick = { navController.popBackStack() },
            onCerTinSignUpClick = { navController.navigateToSignUpPhone() }
        )

        signUpPhoneScreen(
            onBackClick = { navController.popBackStack() },
            onNeighborhoodClick = { navController.navigateToSignUpNeighborhood() }
        )

        signUpNeighborhoodScreen(
            onBackClick = { navController.popBackStack() },
            onIntroduceClick = { navController.navigateToSignUpIntroduce() }
        )

        signUpIntroduceScreen(
            onBackClick = { navController.popBackStack() },
            onNextClick = { navController.navigateToSignUpRecommender() }
        )

        signUpRecommenderScreen(
            onBackClick = { navController.popBackStack() },
            onRecommenderClick = { navController.navigateToSignUpFinish() }
        )

        signUpFinishScreen(
            onClickGoToLogin = { navController.navigateToSignIn() }
        )
    }
}