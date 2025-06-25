package com.school_of_company.gwangsan_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.school_of_company.gwangsan_android.R
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.school_of_company.common.ForBiddenException
import com.school_of_company.common.NoInternetException
import com.school_of_company.common.OtherHttpException
import com.school_of_company.common.ServerException
import com.school_of_company.common.TimeOutException
import com.school_of_company.common.UnKnownException
import com.school_of_company.design_system.componet.toast.makeToast
import com.school_of_company.gwangsan_android.ui.GwangSanAppState
import com.school_of_company.signin.navigation.StartRoute
import com.school_of_company.signin.navigation.navigateToSignIn
import com.school_of_company.signin.navigation.signInScreen
import com.school_of_company.signin.navigation.startScreen
import com.school_of_company.signup.navigation.navigateToSignUpFinish
import com.school_of_company.signup.navigation.navigateToSignUpIntroduce
import com.school_of_company.signup.navigation.navigateToSignUpName
import com.school_of_company.signup.navigation.navigateToSignUpNeighborhood
import com.school_of_company.signup.navigation.navigateToSignUpPassword
import com.school_of_company.signup.navigation.navigateToSignUpPhone
import com.school_of_company.signup.navigation.navigateToSignUpRecommender
import com.school_of_company.signup.navigation.navigateToSignUpStart
import com.school_of_company.signup.navigation.signUpFinishScreen
import com.school_of_company.signup.navigation.signUpIntroduceScreen
import com.school_of_company.signup.navigation.signUpNameScreen
import com.school_of_company.signup.navigation.signUpNeighborhoodScreen
import com.school_of_company.signup.navigation.signUpNickNameScreen
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
    val context = LocalContext.current

    val onErrorToast: (throwable: Throwable?, message: Int?) -> Unit = { throwable, message ->
        val errorMessage = throwable?.let {
            when (it) {
                is ForBiddenException -> R.string.error_forbidden
                is TimeOutException -> R.string.error_time_out
                is ServerException -> R.string.error_server
                is NoInternetException -> R.string.error_no_internet
                is OtherHttpException -> R.string.error_no_internet
                is UnKnownException -> R.string.error_un_known
                else -> message
            }
        } ?: message ?: R.string.error_default

        makeToast(context, context.getString(errorMessage))
    }
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
            onMainClick = { navController.popBackStack() },
            onErrorToast = onErrorToast
        )

        signUpNameScreen(
            onBackClick = { navController.popBackStack() },
            onNicknameClick = { navController.navigateToSignUpName() }
        )

        signUpNickNameScreen(
            onBackClick = {navController.popBackStack() },
            onPasswordClick = { navController.navigateToSignUpPassword() },
            onErrorToast = onErrorToast
        )

        signUpPasswordScreen(
            onBackClick = { navController.popBackStack() },
            onCerTinSignUpClick = { navController.navigateToSignUpPhone() }
        )

        signUpPhoneScreen(
            onBackClick = { navController.popBackStack() },
            onNeighborhoodClick = { navController.navigateToSignUpNeighborhood() },
            onErrorToast = onErrorToast
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