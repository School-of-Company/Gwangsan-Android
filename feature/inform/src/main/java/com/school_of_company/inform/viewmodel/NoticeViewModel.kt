package com.school_of_company.inform.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.data.repository.member.MemberRepository
import com.school_of_company.data.repository.notice.NoticeRepository
import com.school_of_company.inform.viewmodel.uistate.GetAllNoticeUiState
import com.school_of_company.inform.viewmodel.uistate.GetSpecificNoticeUiState
import com.school_of_company.inform.viewmodel.uistate.MemberUiState
import com.school_of_company.result.Result
import com.school_of_company.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val noticeRepository: NoticeRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _getAllNoticeUiState = MutableStateFlow<GetAllNoticeUiState>(GetAllNoticeUiState.Loading)
    internal val getAllNoticeUiState = _getAllNoticeUiState.asStateFlow()

    private val _getSpecificNoticeUiState = MutableStateFlow<GetSpecificNoticeUiState>(GetSpecificNoticeUiState.Loading)
    internal val getSpecificNoticeUiState = _getSpecificNoticeUiState.asStateFlow()

    private val _getMyProfileUiState = MutableStateFlow<MemberUiState>(MemberUiState.Loading)
    internal val getMyProfileUiState = _getMyProfileUiState.asStateFlow()

    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    internal fun getAllNotice() = viewModelScope.launch {
        _swipeRefreshLoading.value = true

        _getAllNoticeUiState.value = GetAllNoticeUiState.Loading

        noticeRepository.getAllNotice()
            .asResult()
            .collectLatest { result ->
                when(result) {
                    is Result.Loading -> _getAllNoticeUiState.value = GetAllNoticeUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getAllNoticeUiState.value = GetAllNoticeUiState.Empty
                            _swipeRefreshLoading.value = false
                        } else {
                            _getAllNoticeUiState.value = GetAllNoticeUiState.Success(result.data)
                            _swipeRefreshLoading.value = false
                        }
                    }
                    is Result.Error -> {
                        _getAllNoticeUiState.value = GetAllNoticeUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
                    }
                }
            }
    }

    internal fun getSpecificNotice(noticeId: Long) = viewModelScope.launch {
        _getSpecificNoticeUiState.value = GetSpecificNoticeUiState.Loading
        noticeRepository.getSpecificNotice(noticeId = noticeId)
            .asResult()
            .collectLatest { result ->
                when(result) {
                    is Result.Loading -> _getSpecificNoticeUiState.value = GetSpecificNoticeUiState.Loading
                    is Result.Success -> _getSpecificNoticeUiState.value = GetSpecificNoticeUiState.Success(result.data)
                    is Result.Error -> _getSpecificNoticeUiState.value = GetSpecificNoticeUiState.Error(result.exception)
                }
            }
    }

    internal fun getMyProfile() = viewModelScope.launch {
        memberRepository.getMyProfileInformation()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getMyProfileUiState.value = MemberUiState.Loading
                    is Result.Success -> _getMyProfileUiState.value = MemberUiState.Success(result.data)
                    is Result.Error -> _getMyProfileUiState.value = MemberUiState.Error(result.exception)
                }
            }
    }
}