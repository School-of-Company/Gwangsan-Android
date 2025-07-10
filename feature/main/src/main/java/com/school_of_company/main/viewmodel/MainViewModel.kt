package com.school_of_company.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.main.viewmodel.uistate.GetMainListUiState
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _getMainListUiState = MutableStateFlow<GetMainListUiState>(GetMainListUiState.Loading)
    val getMainListUiState = _getMainListUiState.asStateFlow()

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
                    is com.school_of_company.result.Result.Loading -> _getMainListUiState.value = GetMainListUiState.Loading
                    is com.school_of_company.result.Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getMainListUiState.value = GetMainListUiState.Empty
                            _swipeRefreshLoading.value = false
                        } else {
                            _getMainListUiState.value = GetMainListUiState.Success(result.data)
                            _swipeRefreshLoading.value = false
                        }
                    }
                    is com.school_of_company.result.Result.Error -> {
                        _getMainListUiState.value = GetMainListUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
                    }
                }
            }
    }
}