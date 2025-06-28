package com.school_of_company.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.Regex.isValidPassword
import com.school_of_company.Regex.isValidPhoneNumber
import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.model.auth.request.SignUpCertificationNumberSendRequestModel
import com.school_of_company.model.auth.request.SignUpRequestModel
import com.school_of_company.network.errorHandling
import com.school_of_company.result.asResult
import com.school_of_company.signup.viewmodel.uistate.SendNumberUiState
import com.school_of_company.signup.viewmodel.uistate.SignUpUiState
import com.school_of_company.signup.viewmodel.uistate.VerifyNumberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val NAME = "name"
        private const val NICKNAME = "nickname"
        private const val PASSWORD = "password"
        private const val RE_PASSWORD = "rePassword"
        private const val PHONE_NUMBER = "phoneNumber"
        private const val CERTIFICATION_NUMBER = "certificationNumber"
        private const val DONG = "dong"
        private const val BRANCH = "branch"
        private const val RECOMMENDER = "recommender"
        private const val SPECIALTY = "specialty"
    }

    private val _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Loading)
    internal val signUpUiState = _signUpUiState.asStateFlow()

    private val _sendNumberUiState = MutableStateFlow<SendNumberUiState>(SendNumberUiState.Loading)
    internal val sendNumberUiState = _sendNumberUiState.asStateFlow()

    private val _verifyNumberUiState = MutableStateFlow<VerifyNumberUiState>(VerifyNumberUiState.Loading)
    internal val verifyNumberUiState = _verifyNumberUiState.asStateFlow()

    private val _specialtyDropdownVisible = MutableStateFlow(false)
    val specialtyDropdownVisible = _specialtyDropdownVisible.asStateFlow()

    private val _branchDropdownVisible = MutableStateFlow(false)
    val branchDropdownVisible = _branchDropdownVisible.asStateFlow()

    internal var name = savedStateHandle.getStateFlow(NAME, "")
    internal var nickname = savedStateHandle.getStateFlow(NICKNAME, "")
    internal var certificationNumber = savedStateHandle.getStateFlow(CERTIFICATION_NUMBER, "")
    internal var number = savedStateHandle.getStateFlow(PHONE_NUMBER, "")
    internal var password = savedStateHandle.getStateFlow(PASSWORD, "")
    internal var checkPassword = savedStateHandle.getStateFlow(RE_PASSWORD, "")
    internal var dong = savedStateHandle.getStateFlow(DONG, "")
    internal var branch = savedStateHandle.getStateFlow(BRANCH, "")
    internal var recommender = savedStateHandle.getStateFlow(RECOMMENDER, "")
    internal var specialty = savedStateHandle.getStateFlow(SPECIALTY, "")

    internal fun signUp(body: SignUpRequestModel) = viewModelScope.launch {
        _signUpUiState.value = SignUpUiState.Loading
        when {
            password.value != checkPassword.value -> {
                _signUpUiState.value = SignUpUiState.PasswordMismatch
            }

            !isValidPassword(body.password) -> {
                _signUpUiState.value = SignUpUiState.PasswordNotValid
            }

            else -> {
                _signUpUiState.value = SignUpUiState.Loading
                authRepository.signUp(body = body)
                    .asResult()
                    .collectLatest { result ->
                        when (result) {
                            is com.school_of_company.result.Result.Success -> _signUpUiState.value =
                                SignUpUiState.Success

                            is com.school_of_company.result.Result.Error -> {
                                _signUpUiState.value = SignUpUiState.Error(result.exception)
                                result.exception.errorHandling(
                                    badRequestAction = { SignUpUiState.BadRequest },
                                    notFoundAction = { SignUpUiState.NotFound },
                                    tooManyRequestAction = { SignUpUiState.TooManyRequest },
                                    conflictAction = { SignUpUiState.Conflict }
                                )
                            }

                            is com.school_of_company.result.Result.Loading -> _signUpUiState.value =
                                SignUpUiState.Loading
                        }
                    }
            }
        }
    }

    internal fun verifyNumber(phoneNumber: String, code: String) =
        viewModelScope.launch {
            _verifyNumberUiState.value = VerifyNumberUiState.Loading
            authRepository.signUpCertificationNumberCertification(
                phoneNumber = phoneNumber,
                code = code
            )
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is com.school_of_company.result.Result.Loading -> _verifyNumberUiState.value =
                            VerifyNumberUiState.Loading

                        is com.school_of_company.result.Result.Success -> _verifyNumberUiState.value =
                            VerifyNumberUiState.Success

                        is com.school_of_company.result.Result.Error -> {
                            _verifyNumberUiState.value = VerifyNumberUiState.Error(result.exception)
                            result.exception.errorHandling(
                                badRequestAction = { SignUpUiState.BadRequest },
                                notFoundAction = { SignUpUiState.NotFound },
                                tooManyRequestAction = { SignUpUiState.TooManyRequest },
                                conflictAction = { SignUpUiState.Conflict }
                            )
                        }
                    }
                }
        }

    internal fun sendCertificationCode(body: SignUpCertificationNumberSendRequestModel) =
        viewModelScope.launch {
            _sendNumberUiState.value = SendNumberUiState.Loading
            authRepository.signUpCertificationNumberSend(body = body)
                .asResult()
                .collectLatest { result ->
                    if (!isValidPhoneNumber(body.phoneNumber)) {
                        _sendNumberUiState.value = SendNumberUiState.PhoneNumberNotValid
                    } else {
                        when (result) {
                            is com.school_of_company.result.Result.Loading -> _sendNumberUiState.value =
                                SendNumberUiState.Loading

                            is com.school_of_company.result.Result.Success -> _sendNumberUiState.value =
                                SendNumberUiState.Success

                            is com.school_of_company.result.Result.Error -> {
                                _sendNumberUiState.value = SendNumberUiState.Error(result.exception)
                                result.exception.errorHandling(
                                    badRequestAction = { SignUpUiState.BadRequest },
                                    notFoundAction = { SignUpUiState.NotFound },
                                    tooManyRequestAction = { SignUpUiState.TooManyRequest },
                                    conflictAction = { SignUpUiState.Conflict })
                            }
                        }
                    }
                }
        }

    private val allAreas = listOf(
        "enw", "chiki", "첨단 3동", "첨단 4동", "첨단 5동"
    )

    val filteredAreas = dong.map { keyword ->
        if (keyword.isBlank()) emptyList()
        else allAreas.filter { it.contains(keyword) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onAreaSelected(value: String) {
        savedStateHandle[DONG] = value
    }

    fun onNameChange(value: String) {
        savedStateHandle[NAME] = value
    }

    internal fun onNicknameChange(value: String) {
        savedStateHandle[NICKNAME] = value
    }

    internal fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }

    internal fun onCheckPasswordChange(value: String) {
        savedStateHandle[RE_PASSWORD] = value
    }

    internal fun onNumberChange(value: String) {
        savedStateHandle[PHONE_NUMBER] = value
    }

    internal fun onCertificationNumberChange(value: String) {
        savedStateHandle[CERTIFICATION_NUMBER] = value
    }

    internal fun onDongChange(value: String) {
        savedStateHandle[DONG] = value
    }

    internal fun onBranchChange(value: String) {
        savedStateHandle[BRANCH] = value
    }

    internal fun onRecommenderChange(value: String) {
        savedStateHandle[RECOMMENDER] = value
    }

    internal fun onSpecialtyChange(value: String) {
        savedStateHandle[SPECIALTY] = value
    }

    internal fun toggleSpecialtyDropdown() {
        _specialtyDropdownVisible.value = !_specialtyDropdownVisible.value
    }

    internal fun closeSpecialtyDropdown() {
        _specialtyDropdownVisible.value = false
    }
}