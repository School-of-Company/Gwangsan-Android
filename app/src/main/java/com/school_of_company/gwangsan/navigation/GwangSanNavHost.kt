package com.school_of_company.gwangsan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.school_of_company.gwangsan.R
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.school_of_company.chat.navigation.chatRoomScreen
import com.school_of_company.chat.navigation.chatScreen
import com.school_of_company.chat.navigation.navigateToChatRoom
import com.school_of_company.common.ForBiddenException
import com.school_of_company.common.NoInternetException
import com.school_of_company.common.OtherHttpException
import com.school_of_company.common.ServerException
import com.school_of_company.common.TimeOutException
import com.school_of_company.common.UnKnownException
import com.school_of_company.content.navigation.navigateToReadMore
import com.school_of_company.content.navigation.readMoreScreen
import com.school_of_company.design_system.component.toast.makeToast
import com.school_of_company.gwangsan.ui.GwangSanAppState
import com.school_of_company.gwangsan.ui.navigateToHomeAndClearLogin
import com.school_of_company.gwangsan.ui.navigationPopUpToLogin
import com.school_of_company.inform.navigation.informDetailScreen
import com.school_of_company.inform.navigation.informScreen
import com.school_of_company.inform.navigation.navigateToInformDetail
import com.school_of_company.main.navgation.mainScreen
import com.school_of_company.main.navgation.mainStartScreen
import com.school_of_company.main.navgation.navigateToMain
import com.school_of_company.main.navgation.navigateToMainStart
import com.school_of_company.main.navgation.navigateToNoticeScreen
import com.school_of_company.main.navgation.noticeScreen
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.navigation.navigateToPost
import com.school_of_company.post.navigation.navigateToPostEdit
import com.school_of_company.post.navigation.postScreen
import com.school_of_company.profile.navigation.myInformationEditScreen
import com.school_of_company.profile.navigation.myProfileScreen
import com.school_of_company.profile.navigation.myReviewScreen
import com.school_of_company.profile.navigation.myWritingDetailScreen
import com.school_of_company.profile.navigation.myWritingScreen
import com.school_of_company.profile.navigation.navigateToMyPeTchWritingDetail
import com.school_of_company.profile.navigation.navigateToMyProfile
import com.school_of_company.profile.navigation.navigateToMyReview
import com.school_of_company.profile.navigation.navigateToMyWriting
import com.school_of_company.profile.navigation.navigateToOtherPersonProfile
import com.school_of_company.profile.navigation.navigateToOtherReview
import com.school_of_company.profile.navigation.otherPersonProfileScreen
import com.school_of_company.profile.navigation.otherReviewScreen
import com.school_of_company.signin.navigation.StartRoute
import com.school_of_company.signin.navigation.navigateToSignIn
import com.school_of_company.signin.navigation.signInScreen
import com.school_of_company.signin.navigation.startScreen
import com.school_of_company.signup.navigation.navigateToSignUpDescription
import com.school_of_company.signup.navigation.navigateToSignUpFinish
import com.school_of_company.signup.navigation.navigateToSignUpIntroduce
import com.school_of_company.signup.navigation.navigateToSignUpName
import com.school_of_company.signup.navigation.navigateToSignUpNeighborhood
import com.school_of_company.signup.navigation.navigateToSignUpPassword
import com.school_of_company.signup.navigation.navigateToSignUpPhone
import com.school_of_company.signup.navigation.navigateToSignUpPlaceName
import com.school_of_company.signup.navigation.navigateToSignUpRecommender
import com.school_of_company.signup.navigation.navigateToSignUpStart
import com.school_of_company.signup.navigation.signUpDescriptionScreen
import com.school_of_company.signup.navigation.signUpFinishScreen
import com.school_of_company.signup.navigation.signUpIntroduceScreen
import com.school_of_company.signup.navigation.signUpNameScreen
import com.school_of_company.signup.navigation.signUpNeighborhoodScreen
import com.school_of_company.signup.navigation.signUpNickNameScreen
import com.school_of_company.signup.navigation.signUpPasswordScreen
import com.school_of_company.signup.navigation.signUpPhoneScreen
import com.school_of_company.signup.navigation.signUpPlaceNameScreen
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
            onMainClick = { navController.navigateToHomeAndClearLogin() },
            onErrorToast = onErrorToast
        )

        signUpNameScreen(
            onBackClick = { navController.popBackStack() },
            onNicknameClick = { navController.navigateToSignUpName() }
        )

        signUpNickNameScreen(
            onBackClick = { navController.popBackStack() },
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
            onIntroduceClick = { navController.navigateToSignUpPlaceName() }
        )

        signUpPlaceNameScreen(
            onBackClick = { navController.popBackStack() },
            onNextClick = { navController.navigateToSignUpIntroduce() }
        )

        signUpIntroduceScreen(
            onBackClick = { navController.popBackStack() },
            onNextClick = { navController.navigateToSignUpDescription() },
            onErrorToast = onErrorToast
        )

        signUpDescriptionScreen(
            onBackClick = { navController.popBackStack() },
            onNextClick = { navController.navigateToSignUpRecommender() }
        )

        signUpRecommenderScreen(
            onBackClick = { navController.popBackStack() },
            onRecommenderClick = { navController.navigateToSignUpFinish() },
            onErrorToast = onErrorToast
        )

        signUpFinishScreen(
            onClickGoToLogin = { navController.navigationPopUpToLogin(loginRoute = StartRoute) }
        )

        mainScreen(
            navigateToDetail = { id ->
                navController.navigateToReadMore(id)
            },
            onBackClick = { navController.popBackStack() },
            onErrorToast = onErrorToast,
        )

        mainStartScreen(
            navigationToService = { navController.navigateToMain("SERVICE") },
            navigationToObject = { navController.navigateToMain("OBJECT") },
            navigationToNotice = { navController.navigateToNoticeScreen() }
        )

        chatScreen(
            onCloseClick = { navController.popBackStack() },
            onChatClick = { productId, roomId ->
                navController.navigateToChatRoom(productId, roomId)
            }
        )

        chatRoomScreen(
            onBackClick = { navController.popBackStack() },
        )

        readMoreScreen(
            onBackClick = { navController.popBackStack() },
            onOtherProfileClick = { memberId ->
                navController.navigateToOtherPersonProfile(memberId = memberId)
            },
            onChatClick = { id, roomId ->
                navController.navigateToChatRoom(id, roomId)
            },
            onReviewClick = { _, _ -> },
            onReportClick = { _, _ -> },
            onEditClick = { id, type, mode ->
                navController.navigateToPostEdit(id)
            }
        )

        informScreen(
            onNextClick = { id ->
                navController.navigateToInformDetail(id)
            }
        )

        informDetailScreen(
            onBackClick = { navController.popBackStack() }
        )

        postScreen(
            onBackClick = { navController.popBackStack() },
            onCreateComplete = { navController.navigateToMainStart() },
            onEditComplete = { navController.popBackStack() }
        )

        myProfileScreen(
            onMyReviewClick = { navController.navigateToMyReview() },
            onMyWritingClick = { navController.navigateToMyWriting() },
            onErrorToast = onErrorToast,
            onMyWritingDetailClick = { id ->
                navController.navigateToReadMore(id)
            },
            onMyInformationEditClick = { navController.navigateToMyPeTchWritingDetail() },
            onLogoutClick = { navController.navigationPopUpToLogin(loginRoute = StartRoute) }
        )

        otherPersonProfileScreen(
            onBackClick = { navController.popBackStack() },
            onErrorToast = onErrorToast,
            onOtherReviewClick = { id ->
                navController.navigateToOtherReview(id)
            },
            onOtherWritingDetailClick = { id ->
                navController.navigateToReadMore(id)
            }
        )

        myInformationEditScreen(
            onBackClick = { navController.popBackStack() },
            onSubmitComplete = {
                navController.navigateToMyProfile()
            },
            onErrorToast = onErrorToast
        )

        myReviewScreen(
            onBackClick = { navController.popBackStack() },
            onPostClick = { id -> navController.navigateToReadMore(id) }
        )


        myWritingScreen(
            onBackClick = { navController.popBackStack() },
            onPostClick = { id -> navController.navigateToReadMore(id) }

        )

        myWritingDetailScreen(
            onBackClick = { navController.popBackStack() },
            onCompleteClick = { navController.popBackStack() },
            onErrorToast = onErrorToast,
            onEditClick = { id, type, mode ->
                navController.navigateToPostEdit(id)
            }
        )

        otherReviewScreen(
            onBackClick = { navController.popBackStack() },
            onPostClick = { id -> navController.navigateToReadMore(id) }
        )

        noticeScreen(
            onBackClick = { navController.popBackStack() },
            navigationToDetail = { id ->
                navController.navigateToReadMore(id)
            },
            navigateToInformDetail = { id ->
                navController.navigateToInformDetail(id)
            }
        )
    }
}
