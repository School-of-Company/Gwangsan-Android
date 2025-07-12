package com.school_of_company.content.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.content.viewmodel.uistate.GetSpecificPostUiState
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.result.Result
import com.school_of_company.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _getSpecificPostUiState = MutableStateFlow<GetSpecificPostUiState>(GetSpecificPostUiState.Loading)
    internal val getSpecificPostUiState = _getSpecificPostUiState.asStateFlow()

    internal fun getSpecificPost(postId: Long) = viewModelScope.launch {
        _getSpecificPostUiState.value = GetSpecificPostUiState.Loading
        postRepository.getSpecificInformation(postId = postId)
            .asResult()
            .collectLatest { result ->
                when(result) {
                    is Result.Loading -> _getSpecificPostUiState.value = GetSpecificPostUiState.Loading
                    is Result.Success -> _getSpecificPostUiState.value = GetSpecificPostUiState.Success(result.data)
                    is Result.Error -> _getSpecificPostUiState.value = GetSpecificPostUiState.Error(result.exception)
                }
            }
    }
}