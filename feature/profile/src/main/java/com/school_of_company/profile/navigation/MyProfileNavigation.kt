package com.school_of_company.profile.navigation

import ReviewPostDetailRoute
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.school_of_company.profile.view.MyProfileRoute
import com.school_of_company.profile.view.MyReviewRoute
import com.school_of_company.profile.view.MyWritingRoute
import com.school_of_company.profile.view.TransactionHistoryRoute


const val MyProfileRoute = "my_profile"
const val MyReviewRoute = "my_review"
const val MyWritingRoute = "my_writing"
const val MyWritingDetailRoute = "my_writing_detail"
const val TransactionHistoryRoute = "transaction_history"

fun NavController.navigateToMyProfile(navOptions: NavOptions? = null) {
    this.navigate(MyProfileRoute, navOptions)
}

fun NavController.navigateToMyReview(navOptions: NavOptions? = null) {
    this.navigate(MyReviewRoute, navOptions)
}

fun NavController.navigateToMyWriting(navOptions: NavOptions? = null) {
    this.navigate(MyWritingRoute, navOptions)
}

fun NavController.navigateToMyWritingDetail(navOptions: NavOptions? = null) {
    this.navigate(MyWritingDetailRoute, navOptions)
}

fun NavController.navigateToTransactionHistory(navOptions: NavOptions? = null) {
    this.navigate(TransactionHistoryRoute, navOptions)
}

fun NavGraphBuilder.myProfileScreen(
    onMyReviewClick: () -> Unit,
    onMyWritingClick: () -> Unit,
    onTransactionHistoryClick: () -> Unit
) {
    composable(route = MyProfileRoute) {
        MyProfileRoute(
            onMyReviewClick = onMyReviewClick,
            onMyWritingClick = onMyWritingClick,
            onTransactionHistoryClick = onTransactionHistoryClick
        )
    }
}

fun NavGraphBuilder.myReviewScreen(
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
) {
    composable(route = MyReviewRoute) {
        MyReviewRoute(
            onBackClick = onBackClick,
            onMyProfileClick = onMyProfileClick
        )
    }
}

fun NavGraphBuilder.myWritingScreen(
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
    onReviewPostDetailClick: () -> Unit,
) {
    composable(route = MyWritingRoute) {
        MyWritingRoute (
            onBackClick = onBackClick,
            onMyProfileClick = onMyProfileClick,
            onReviewPostDetailClick = onReviewPostDetailClick
        )
    }
}

fun NavGraphBuilder.myWritingDetailScreen(
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
    onCompleteClick: () -> Unit,
)
{
    composable(route = MyWritingDetailRoute) {
        ReviewPostDetailRoute(
            onBackClick = onBackClick,
            onMyProfileClick = onMyProfileClick,
            onCompleteClick = onCompleteClick
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
