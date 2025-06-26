package com.school_of_company.main.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.data.repository.main.MainRepository
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
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle,
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
        mainRepository.allPostGeT(
            type = type,
            mode = mode
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
                        Log.e("MainViewModel", "❌ Error 발생", result.exception)
                        _getMainListUiState.value = GetMainListUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
                    }
                }
            }
    }

}