package com.school_of_company.post.viewmodel.uiState

import com.school_of_company.model.image.response.ImageUpLoadResponseModel

interface ImageUpLoadUiState {
    data object Loading : ImageUpLoadUiState
    data class Success(val data: ImageUpLoadResponseModel) : ImageUpLoadUiState
    data class Error(val exception: Throwable) : ImageUpLoadUiState
}