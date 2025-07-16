package com.school_of_company.profile.viewmodel.uistate

import com.school_of_company.model.review.response.ReviewResponseModel

sealed interface GetMyReviewWriteUiState {
    data object Loading : GetMyReviewWriteUiState
    data object Empty : GetMyReviewWriteUiState
    data class Success(val data: List<ReviewResponseModel>) : GetMyReviewWriteUiState
    data class Error(val exception: Throwable) : GetMyReviewWriteUiState
}