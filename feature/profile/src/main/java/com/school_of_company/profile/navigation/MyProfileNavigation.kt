package com.school_of_company.profile.navigation

import MyReviewRoute
import ReviewPostDetailRoute
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.profile.view.MyInformationEditRoute
import com.school_of_company.profile.view.MyProfileRoute
import com.school_of_company.profile.view.MyReceiveReviewRoute
import com.school_of_company.profile.view.OtherPersonProfileRoute
import com.school_of_company.profile.view.TransactionHistoryRoute


const val MyProfileRoute = "my_profile"
const val MyReviewRoute = "my_review"
const val MyWritingRoute = "my_writing"
const val MyWritingDetailRoute = "my_writing_detail"
const val TransactionHistoryRoute = "transaction_history"
const val OtherPersonProfileRoute = "other_person_profile"
const val MyPeTchPostDetailRoute = "review_post_detail"

fun NavController.navigateToOtherPersonProfile(navOptions: NavOptions? = null) {
    this.navigate(OtherPersonProfileRoute, navOptions)
}

fun NavController.navigateToReviewPostDetail(navOptions: NavOptions? = null) {
    this.navigate(MyPeTchPostDetailRoute, navOptions)
}

fun NavController.navigateToMyProfile(navOptions: NavOptions? = null) {
    this.navigate(MyProfileRoute, navOptions)
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
    onErrorToast: (Throwable, Int) -> Unit
) {
    composable("$OtherPersonProfileRoute/{memberId}") { backStackEntry ->
        val memberId = backStackEntry.arguments?.getString("memberId")?.toLongOrNull() ?: return@composable

        OtherPersonProfileRoute(
            memberId = memberId,
            onErrorToast = onErrorToast
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
    onMyWritingDetailClick: (Int) -> Unit,
    onErrorToast: (Throwable, Int) -> Unit
) {
    composable(route = MyProfileRoute) {
        MyProfileRoute(
            onMyReviewClick = onMyReviewClick,
            onMyWritingClick = onMyWritingClick,
            onMyWritingDetailClick = onMyWritingDetailClick,
            onErrorToast = onErrorToast
        )
    }
}


fun NavGraphBuilder.myReviewScreen(
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
) {
    composable(route = MyReviewRoute) {
        MyReceiveReviewRoute(
            onBackClick = onBackClick,
            onMyProfileClick = onMyProfileClick
        )
    }
}

fun NavGraphBuilder.myWritingScreen(
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
) {
    composable(route = MyWritingRoute) {
        MyReviewRoute(
            onBackClick = onBackClick,
            onMyProfileClick = onMyProfileClick,
        )
    }
}

fun NavGraphBuilder.myWritingDetailScreen(
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onErrorToast: (Throwable, Int) -> Unit
)
{
    composable(route = MyWritingDetailRoute) {
        ReviewPostDetailRoute(
            onBackClick = onBackClick,
            onMyProfileClick = onMyProfileClick,
            onCompleteClick = onCompleteClick,
            onErrorToast = onErrorToast
        )
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
