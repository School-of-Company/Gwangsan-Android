package com.school_of_company.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.data.repository.alert.AlertRepository
import com.school_of_company.data.repository.member.MemberRepository
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.main.viewmodel.uistate.GetAlertUiState
import com.school_of_company.main.viewmodel.uistate.GetMainListUiState
import com.school_of_company.main.viewmodel.uistate.MemberUiState
import com.school_of_company.main.viewmodel.uistate.TransactionCompleteUiState
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.model.post.request.TransactionCompleteRequestModel
import com.school_of_company.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.school_of_company.result.Result

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
    private val alertRepository: AlertRepository,
) : ViewModel() {

    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _getMainListUiState = MutableStateFlow<GetMainListUiState>(GetMainListUiState.Loading)
    val getMainListUiState = _getMainListUiState.asStateFlow()

    private val _getAlertUiState = MutableStateFlow<GetAlertUiState>(GetAlertUiState.Loading)
    val getAlertUiState = _getAlertUiState.asStateFlow()

    private val _transactionCompleteUiState = MutableStateFlow<TransactionCompleteUiState>(TransactionCompleteUiState.Loading)
    internal val transactionCompleteUiState = _transactionCompleteUiState.asStateFlow()

    private val _myProfileUiState = MutableStateFlow<MemberUiState>(MemberUiState.Loading)
    internal val myProfileUiState = _myProfileUiState.asStateFlow()

    internal fun getMainList(
        type: Type,
        mode: Mode,
    ) = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        postRepository.getAllPostInformation(
            type = type.name,
            mode = mode.name
        )
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getMainListUiState.value = GetMainListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getMainListUiState.value = GetMainListUiState.Empty
                            _swipeRefreshLoading.value = false
                        } else {
                            _getMainListUiState.value = GetMainListUiState.Success(result.data)
                            _swipeRefreshLoading.value = false
                        }
                    }
                    is Result.Error -> {
                        _getMainListUiState.value = GetMainListUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
                    }
                }
            }
    }

    internal fun getAlert() = viewModelScope.launch {
        alertRepository.getAlert()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getAlertUiState.value = GetAlertUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _swipeRefreshLoading.value = false
                            _getAlertUiState.value = GetAlertUiState.Empty
                        } else {
                            _swipeRefreshLoading.value = false
                            _getAlertUiState.value = GetAlertUiState.Success(result.data)
                        }
                    }
                    is Result.Error -> {
                        _swipeRefreshLoading.value = false
                        _getAlertUiState.value = GetAlertUiState.Error(result.exception)
                    }
                }
            }
    }

    internal fun getMyProfile() = viewModelScope.launch {
        memberRepository.getMyProfileInformation()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _myProfileUiState.value = MemberUiState.Loading
                    is Result.Success -> _myProfileUiState.value = MemberUiState.Success(result.data)
                    is Result.Error -> _myProfileUiState.value = MemberUiState.Error(result.exception)
                }
            }
    }

    internal fun transactionComplete(body: TransactionCompleteRequestModel) = viewModelScope.launch {
        _transactionCompleteUiState.value = TransactionCompleteUiState.Loading
        postRepository.transactionComplete(body = body)
            .asResult()
            .collectLatest { result ->
                when (result){
                    is Result.Loading -> _transactionCompleteUiState.value = TransactionCompleteUiState.Loading
                    is Result.Success -> _transactionCompleteUiState.value = TransactionCompleteUiState.Success
                    is Result.Error -> _transactionCompleteUiState.value = TransactionCompleteUiState.Error(result.exception)
                }
            }
    }
}