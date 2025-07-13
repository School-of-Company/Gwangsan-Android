package com.school_of_company.profile.viewmodel.uistate

import com.school_of_company.model.review.response.ReviewResponseModel

sealed interface OtherReviewUIState {
    data object Loading : OtherReviewUIState
    data object Empty : OtherReviewUIState
    data class Success(val data: ReviewResponseModel) : OtherReviewUIState
    data class Error(val exception: Throwable) : OtherReviewUIState
}