package com.school_of_company.signin.viewmodel.uistate

import com.school_of_company.model.auth.response.LoginResponseModel

sealed interface SignInUiState {
    object Loading : SignInUiState
    object Success : SignInUiState
    object BadRequest : SignInUiState
    object NotFound : SignInUiState
    object IdNotValid : SignInUiState
    data class Error(val exception: Throwable) : SignInUiState
}