package com.school_of_company.signin.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.Regex.isValidId
import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.data.repository.local.LocalRepository
import com.school_of_company.model.auth.request.LoginRequestModel
import com.school_of_company.network.errorHandling
import com.school_of_company.network.util.DeviceIdManager
import com.school_of_company.result.asResult
import com.school_of_company.result.Result
import com.school_of_company.signin.viewmodel.uistate.SaveTokenUiState
import com.school_of_company.signin.viewmodel.uistate.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
    private val localRepository: LocalRepository
) : ViewModel() {
    companion object {
        private const val ID = "id"
        private const val PASSWORD = "password"
    }

    private val _signInUiState = MutableStateFlow<SignInUiState>(SignInUiState.Loading)
    internal val signInUiState = _signInUiState.asStateFlow()

    private val _saveTokenUiState = MutableStateFlow<SaveTokenUiState>(SaveTokenUiState.Loading)
    internal val saveTokenUiState = _saveTokenUiState.asStateFlow()

    internal var id = savedStateHandle.getStateFlow(key = ID, initialValue = "")
    internal var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    internal fun login(deviceId: UUID) = viewModelScope.launch {
        val nicknameValue = id.value
        val passwordValue = password.value

        val deviceToken = localRepository.getDeviceToken()

        if (!isValidId(nicknameValue)) {
            _signInUiState.value = SignInUiState.IdNotValid
            return@launch
        }

        val body = LoginRequestModel(
            nickname = nicknameValue,
            password = passwordValue,
            deviceToken = deviceToken,
            deviceId = deviceId.toString()
        )

        authRepository.signIn(body)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _signInUiState.value = SignInUiState.Loading
                    }
                    is Result.Success -> {
                        _signInUiState.value = SignInUiState.Success
                        authRepository.saveToken(result.data)
                    }
                    is Result.Error -> {
                        _signInUiState.value = SignInUiState.Error(result.exception)
                        result.exception.errorHandling(notFoundAction = { _signInUiState.value = SignInUiState.NotFound })
                    }
                }
            }
    }

    internal fun onIdChange(value: String) {
        savedStateHandle[ID] = value
    }

    internal fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }
}

