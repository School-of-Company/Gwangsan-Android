package com.school_of_company.signup.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.Regex.isValidPassword
import com.school_of_company.Regex.isValidPhoneNumber
import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.model.auth.request.SignUpCertificationNumberSendRequestModel
import com.school_of_company.model.auth.request.SignUpRequestModel
import com.school_of_company.model.auth.request.SmsVerifyCodeRequestModel
import com.school_of_company.network.errorHandling
import com.school_of_company.result.asResult
import com.school_of_company.signup.viewmodel.uistate.SendNumberUiState
import com.school_of_company.signup.viewmodel.uistate.SignUpUiState
import com.school_of_company.signup.viewmodel.uistate.VerifyNumberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.school_of_company.result.Result
import kotlinx.collections.immutable.persistentListOf

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
        private const val DESCRIPTION = "description"
        private const val RECOMMENDER = "recommender"
        private const val SPECIALTY = "specialty"
        private const val SPECIALTY_TEXT = "specialtyText"
        private const val PLACE_NAME = "placeName"
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
    internal var description = savedStateHandle.getStateFlow(DESCRIPTION, "")
    internal var recommender = savedStateHandle.getStateFlow(RECOMMENDER, "")
    internal var specialty = savedStateHandle.getStateFlow(SPECIALTY, emptyList<String>())
    internal var specialtyText = savedStateHandle.getStateFlow(SPECIALTY_TEXT, "")
    internal var placeName = savedStateHandle.getStateFlow(PLACE_NAME, "")

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
                            is Result.Success -> _signUpUiState.value =
                                SignUpUiState.Success

                            is Result.Error -> {
                                _signUpUiState.value = SignUpUiState.Error(result.exception)
                                result.exception.errorHandling(
                                    conflictAction = { _signUpUiState.value = SignUpUiState.Conflict },
                                    unauthorizedAction = { _signUpUiState.value = SignUpUiState.Unauthorized },
                                )
                            }

                            is Result.Loading -> _signUpUiState.value = SignUpUiState.Loading
                        }
                    }
            }
        }
    }

    internal fun verifyNumber(phoneNumber: String, code: String) =
        viewModelScope.launch {
            _verifyNumberUiState.value = VerifyNumberUiState.Loading
            authRepository.signUpCertificationNumberCertification(
                body = SmsVerifyCodeRequestModel(
                    phoneNumber = phoneNumber,
                    code = code
                )
            )
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> _verifyNumberUiState.value =
                            VerifyNumberUiState.Loading

                        is Result.Success -> _verifyNumberUiState.value =
                            VerifyNumberUiState.Success

                        is Result.Error -> {
                            _verifyNumberUiState.value = VerifyNumberUiState.Error(result.exception)
                            result.exception.errorHandling(
                                badRequestAction = { _verifyNumberUiState.value = VerifyNumberUiState.BadRequest },
                                unauthorizedAction = { _verifyNumberUiState.value = VerifyNumberUiState.Unauthorized },
                                forbiddenAction = { _verifyNumberUiState.value = VerifyNumberUiState.Forbidden }
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
                            is Result.Loading -> _sendNumberUiState.value =
                                SendNumberUiState.Loading

                            is Result.Success -> _sendNumberUiState.value =
                                SendNumberUiState.Success

                            is Result.Error -> {
                                _sendNumberUiState.value = SendNumberUiState.Error(result.exception)
                                result.exception.errorHandling(
                                    tooManyRequestAction = { _sendNumberUiState.value =  SendNumberUiState.TooManyRequest },
                                    badRequestAction = { _sendNumberUiState.value = SendNumberUiState.PhoneNumberNotValid },
                                    conflictAction = { _sendNumberUiState.value = SendNumberUiState.Conflict}
                                )

                            }
                        }
                    }
                }
        }

    private val allAreas = persistentListOf(
        "동곡동",
        "도산동",
        "평동",
        "운남동",
        "첨단1동",
        "첨단2동",
        "송정1동",
        "송정2동",
        "우산동",
        "신가동",
        "신흥동",
        "수완동",
        "임곡동",
        "본량동",
        "월곡1동",
        "월곡2동",
        "하남동",
        "비아동",
        "어룡동",
        "삼도동"
    )

    @OptIn(FlowPreview::class)
    val filteredAreas = dong
        .debounce(100)
        .map { keyword ->
            if (keyword.isBlank()) {
                emptyList()
            } else {
                allAreas.filter {
                    it.contains(keyword, ignoreCase = true)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val defaultSpecialties = listOf("청소하기", "운전하기", "달리기", "빨래하기", "벌레잡기", "이삿짐 나르기")

    val specialtyOptions = specialty
        .map { userAdded ->
            (defaultSpecialties + userAdded).distinct()
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, defaultSpecialties)

    fun addSpecialty() {
        val currentInput = specialtyText.value.trim()
        if (currentInput.isNotBlank()) {
            val currentSelected = specialty.value
            if (!currentSelected.contains(currentInput)) {
                savedStateHandle[SPECIALTY] = currentSelected + currentInput
            }

            savedStateHandle[SPECIALTY_TEXT] = ""
        }
    }
    
    fun removeSpecialty(item: String) {
        savedStateHandle[SPECIALTY] = specialty.value - item
    }

    fun onAreaSelected(value: String) {
        savedStateHandle[DONG] = value
    }

    fun onSpecialtyTextChange(value: String) {
        savedStateHandle[SPECIALTY_TEXT] = value
    }

    fun onNameChange(value: String) {
        savedStateHandle[NAME] = value.trim()
    }

    internal fun onNicknameChange(value: String) {
        savedStateHandle[NICKNAME] = value.trim()
    }

    internal fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value.trim()
    }

    internal fun onCheckPasswordChange(value: String) {
        savedStateHandle[RE_PASSWORD] = value.trim()
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

    internal fun onDescriptionChange(value: String) {
        savedStateHandle[DESCRIPTION] = value
    }

    internal fun onRecommenderChange(value: String) {
        savedStateHandle[RECOMMENDER] = value.trim()
    }

    internal fun onSpecialtyListChange(list: List<String>) {
        savedStateHandle[SPECIALTY] = list
    }

    internal fun toggleSpecialtyDropdown() {
        _specialtyDropdownVisible.value = !_specialtyDropdownVisible.value
    }

    internal fun closeSpecialtyDropdown() {
        _specialtyDropdownVisible.value = false
    }

    internal fun onPlaceNameChange(value: String) {
        savedStateHandle[PLACE_NAME] = value
    }
}