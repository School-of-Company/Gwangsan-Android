package com.school_of_company.profile.viewmodel.uistate

import com.school_of_company.model.post.response.Post
import com.school_of_company.model.review.response.ReviewResponseModel

sealed interface GetMyReviewUiState {
    data object Loading : GetMyReviewUiState
    data object Empty : GetMyReviewUiState
    data class Success(val review: List<ReviewResponseModel>) : GetMyReviewUiState
    data class Error(val exception: Throwable) : GetMyReviewUiState
}