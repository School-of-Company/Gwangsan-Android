package com.school_of_company.profile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.data.repository.member.MemberRepository
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.model.member.request.ModifyMemberInformationRequestModel
import com.school_of_company.profile.viewmodel.uistate.GetMyPostUiState
import com.school_of_company.profile.viewmodel.uistate.GetMySpecificInformationUiState
import com.school_of_company.profile.viewmodel.uistate.LogoutUiState
import com.school_of_company.profile.viewmodel.uistate.MemberUiState
import com.school_of_company.profile.viewmodel.uistate.MyInForMatIonPeTchUiState
import com.school_of_company.profile.viewmodel.uistate.OtherPersonGetUistate
import com.school_of_company.result.asResult
import com.school_of_company.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MyProfileViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val DESCRIPTION = "description"
        private const val SPECIALTY = "specialty"
        private const val SPECIALTY_LIST = "specialty_list"
        private const val NICKNAME = "nickname"
    }

    private val _logoutUiState = MutableStateFlow<LogoutUiState>(LogoutUiState.Loading)
    internal val logoutUiState = _logoutUiState.asStateFlow()

    private val _myProfileUiState = MutableStateFlow<MemberUiState>(MemberUiState.Loading)
    internal val myProfileUiState = _myProfileUiState.asStateFlow()

    private val _getMySpecificInformationUiState =
        MutableStateFlow<GetMySpecificInformationUiState>(GetMySpecificInformationUiState.Loading)
    internal val getMySpecificInformationUiState = _getMySpecificInformationUiState.asStateFlow()

    private val _getMyPostUiState = MutableStateFlow<GetMyPostUiState>(GetMyPostUiState.Loading)
    internal val getMyPostUiState = _getMyPostUiState.asStateFlow()

    private val _otherPersonUiState =
        MutableStateFlow<OtherPersonGetUistate>(OtherPersonGetUistate.Loading)
    internal val otherPersonUiState = _otherPersonUiState.asStateFlow()

    private val _myInformationPatchUiState =
        MutableStateFlow<MyInForMatIonPeTchUiState?>(MyInForMatIonPeTchUiState.Loading)
    internal val myInformationPatchUiState = _myInformationPatchUiState.asStateFlow()

    internal val nickname = savedStateHandle.getStateFlow(NICKNAME, "")
    internal val specialty = savedStateHandle.getStateFlow(SPECIALTY, "")
    internal val specialtyList = savedStateHandle.getStateFlow(SPECIALTY_LIST, emptyList<String>())
    internal val description = savedStateHandle.getStateFlow(DESCRIPTION, "")

    internal fun logout() = viewModelScope.launch {
        authRepository.logout()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _logoutUiState.value = LogoutUiState.Loading

                    is Result.Success -> _logoutUiState.value = LogoutUiState.Success

                    is Result.Error -> _logoutUiState.value = LogoutUiState.Error(result.exception)
                }
            }
    }

    internal fun getMyProfile() = viewModelScope.launch {
        memberRepository.getMyProfileInformation()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _myProfileUiState.value = MemberUiState.Loading

                    is Result.Success -> {
                        _myProfileUiState.value = MemberUiState.Success(result.data)

                        result.data.let { profileText ->
                            onNickNameChange(profileText.nickname)
                            onSpecialtyListChange(profileText.specialties)
                            onDescriptionChange(profileText.description)
                        }
                    }

                    is Result.Error -> {
                        _myProfileUiState.value = MemberUiState.Error(result.exception)
                    }
                }
            }
    }

    internal fun otherPersonGetMyProfile(memberId: Long) = viewModelScope.launch {
        memberRepository.getSpecificMemberProfileInformation(
            memberId = memberId
        )
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _otherPersonUiState.value = OtherPersonGetUistate.Loading

                    is Result.Success -> _otherPersonUiState.value =
                        OtherPersonGetUistate.Success(result.data)

                    is Result.Error -> _otherPersonUiState.value =
                        OtherPersonGetUistate.Error(result.exception)
                }
            }
    }

    internal fun myInformationPatch(body: ModifyMemberInformationRequestModel) =
        viewModelScope.launch {
            memberRepository.editMyProfileInformation(body = body)
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> _myInformationPatchUiState.value =
                            MyInForMatIonPeTchUiState.Loading

                        is Result.Success -> _myInformationPatchUiState.value =
                            MyInForMatIonPeTchUiState.Success

                        is Result.Error -> _myInformationPatchUiState.value =
                            MyInForMatIonPeTchUiState.Error(result.exception)
                    }
                }
        }

    internal fun getMyPost() = viewModelScope.launch {
        postRepository.getMyPostInformation(
            type = null,
            mode = null
        )
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getMyPostUiState.value = GetMyPostUiState.Loading

                    is Result.Success ->
                        if (result.data.isEmpty()) {
                            _getMyPostUiState.value = GetMyPostUiState.Empty
                        } else {
                            _getMyPostUiState.value = GetMyPostUiState.Success(result.data)
                        }

                    is Result.Error -> _getMyPostUiState.value =
                        GetMyPostUiState.Error(result.exception)
                }
            }
    }

    internal fun getMyPostDetail(postId: Long) = viewModelScope.launch {
        postRepository.getSpecificInformation(postId = postId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getMySpecificInformationUiState.value =
                        GetMySpecificInformationUiState.Loading

                    is Result.Success -> _getMySpecificInformationUiState.value =
                        GetMySpecificInformationUiState.Success(result.data)

                    is Result.Error -> _getMySpecificInformationUiState.value =
                        GetMySpecificInformationUiState.Error(result.exception)
                }
            }
    }

    fun addSpecialty() {
        val currentInput = specialty.value.trim()
        if (currentInput.isNotBlank()) {
            val currentSelected = specialtyList.value
            if (!currentSelected.contains(currentInput)) {
                savedStateHandle[SPECIALTY_LIST] = currentSelected + currentInput
            }

            savedStateHandle[SPECIALTY] = ""
        }
    }

    fun removeSpecialty(item: String) {
        savedStateHandle[SPECIALTY_LIST] = specialtyList.value - item
    }

    internal fun onNickNameChange(value: String) {
        savedStateHandle[NICKNAME] = value
    }

    internal fun onSpecialtyChange(value: String) {
        savedStateHandle[SPECIALTY] = value
    }

    internal fun onSpecialtyListChange(value: List<String>) {
        savedStateHandle[SPECIALTY_LIST] = value
    }

    internal fun onDescriptionChange(value: String) {
        savedStateHandle[DESCRIPTION] = value
    }
}
