package com.school_of_company.profile.navigation

import MyReviewRoute
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.profile.view.MyInformationEditRoute
import com.school_of_company.profile.view.MyProfileRoute
import com.school_of_company.profile.view.MyReceiveReviewRoute
import com.school_of_company.profile.view.OtherPersonProfileRoute
import com.school_of_company.profile.view.OtherReviewRoute
import com.school_of_company.profile.view.ReviewPostDetailRoute
import com.school_of_company.profile.view.TransactionHistoryRoute

const val MyProfileRoute = "my_profile"
const val MyReviewRoute = "my_review"
const val MyWritingRoute = "my_writing"
const val MyWritingDetailRoute = "my_writing_detail"
const val TransactionHistoryRoute = "transaction_history"
const val OtherPersonProfileRoute = "other_person_profile"
const val MyPeTchPostDetailRoute = "review_post_detail"
const val OtherReviewRoute = "review_other_review"

fun NavController.navigateToOtherPersonProfile(
    memberId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$OtherPersonProfileRoute/${memberId}",
        navOptions =  navOptions
    )
}

fun NavController.navigateToPostDetail(
    postId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$MyWritingDetailRoute/${postId}",
        navOptions = navOptions
    )
}

fun NavController.navigateToMyProfile(navOptions: NavOptions? = null) {
    this.navigate(MyProfileRoute, navOptions)
}

fun NavController.navigateToOtherReview(
    memberId: Long,
    navOptions: NavOptions? = null){
    this.navigate("$OtherReviewRoute/${memberId}", navOptions)
}

fun NavController.navigateToMyReview(navOptions: NavOptions? = null) {
    this.navigate(MyReviewRoute, navOptions)
}

fun NavController.navigateToMyWriting(navOptions: NavOptions? = null) {
    this.navigate(MyWritingRoute, navOptions)
}

fun NavController.navigateToMyPeTchWritingDetail(navOptions: NavOptions? = null) {
    this.navigate(MyPeTchPostDetailRoute, navOptions)
}

fun NavGraphBuilder.otherPersonProfileScreen(
    onBackClick: () -> Unit,
    onErrorToast: (Throwable, Int) -> Unit,
    onOtherReviewClick: (Long) -> Unit,
    onOtherWritingDetailClick: (Long) -> Unit,
) {
    composable("$OtherPersonProfileRoute/{memberId}") { backStackEntry ->
        val memberId = backStackEntry.arguments?.getString("memberId")?.toLongOrNull() ?: return@composable

        OtherPersonProfileRoute(
            memberId = memberId,
            onBackClick = onBackClick,
            onErrorToast = onErrorToast,
            onOtherReviewClick = onOtherReviewClick,
            onOtherWritingDetailClick = onOtherWritingDetailClick
        )
    }
}

fun NavGraphBuilder.myInformationEditScreen(
    onBackClick: () -> Unit,
    onSubmitComplete: () -> Unit,
    onErrorToast: (Throwable?, Int?) -> Unit
){
    composable(route = MyPeTchPostDetailRoute) {
        MyInformationEditRoute(
            onBackClick = onBackClick,
            onSubmitComplete = onSubmitComplete,
            onErrorToast = onErrorToast
        )
    }
}

fun NavGraphBuilder.myProfileScreen(
    onMyReviewClick: () -> Unit,
    onMyWritingClick: () -> Unit,
    onMyWritingDetailClick: (Long) -> Unit,
    onMyInformationEditClick: () -> Unit,
    onErrorToast: (Throwable, Int) -> Unit,
    onLogoutClick: () -> Unit
) {
    composable(route = MyProfileRoute) {
        MyProfileRoute(
            onMyReviewClick = onMyReviewClick,
            onMyWritingClick = onMyWritingClick,
            onMyWritingDetailClick = onMyWritingDetailClick,
            onErrorToast = onErrorToast,
            onMyInformationEditClick = onMyInformationEditClick,
            onLogoutClick = onLogoutClick
        )
    }
}


fun NavGraphBuilder.myReviewScreen(
    onBackClick: () -> Unit,
) {
    composable(route = MyReviewRoute) {
        MyReceiveReviewRoute(
            onBackClick = onBackClick,
        )
    }
}

fun NavGraphBuilder.myWritingScreen(
    onBackClick: () -> Unit,
) {
    composable(route = MyWritingRoute) {
        MyReviewRoute(
            onBackClick = onBackClick,
        )
    }
}

fun NavGraphBuilder.myWritingDetailScreen(
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onErrorToast: (Throwable, Int) -> Unit,
    onEditClick: (Long, String, String) -> Unit,
    ) {
    composable(route = "$MyWritingDetailRoute/{postId}") { backStackEntry ->
        val postId = backStackEntry.arguments?.getString("postId")?.toLongOrNull()
        if (postId != null) {
            ReviewPostDetailRoute(
                postId = postId,
                onBackClick = onBackClick,
                onCompleteClick = onCompleteClick,
                onErrorToast = onErrorToast,
                onEditClick = onEditClick
            )
        }
    }
}

fun NavGraphBuilder.transactionHistoryScreen(
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
) {
    composable(route = TransactionHistoryRoute) {
        TransactionHistoryRoute(
            onBackClick = onBackClick,
            onMyProfileClick = onMyProfileClick
        )
    }
}

fun NavGraphBuilder.otherReviewScreen(
    onBackClick: () -> Unit,
){
    composable( "$OtherReviewRoute/{memberId}"){ backStackEntry ->
        val memberId = backStackEntry.arguments?.getString("memberId")?.toLongOrNull() ?: return@composable

        OtherReviewRoute(
            onBackClick =  onBackClick,
            memberId = memberId
        )
    }
}
