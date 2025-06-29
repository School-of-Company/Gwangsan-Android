package com.school_of_company.post.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.dto.post.request.PostWriteRequest
import com.school_of_company.network.errorHandling
import com.school_of_company.result.asResult
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.post.viewmodel.uiState.PostUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val TITLE = "title"
        private const val CONTENT = "content"
        private const val GWANGSAN = "gwangsan"
        private const val IMAGE_IDS = "imageIds"
        private const val TYPE = "type"
        private const val MODE = "mode"
    }

    private val _postUiState = MutableStateFlow<PostUiState>(PostUiState.Loading)
    val postUiState = _postUiState.asStateFlow()

    internal val title = savedStateHandle.getStateFlow(TITLE, "")
    internal val content = savedStateHandle.getStateFlow(CONTENT, "")
    internal val gwangsan = savedStateHandle.getStateFlow(GWANGSAN, "")
    internal val imageIds = savedStateHandle.getStateFlow(IMAGE_IDS, emptyList<String>())
    internal val type = savedStateHandle.getStateFlow(TYPE, Type.OBJECT)
    internal val mode = savedStateHandle.getStateFlow(MODE, Mode.GIVER)

    internal fun writePost() = viewModelScope.launch {
        if (title.value.isBlank() || content.value.isBlank()) {
            _postUiState.value = PostUiState.Error(IllegalArgumentException("빈 값 존재"))
            return@launch
        }

        _postUiState.value = PostUiState.Loading
        postRepository.writePostInformation(
            type = type.value,
            mode = mode.value,
            request = PostWriteRequest(
                type = type.value,
                mode = mode.value,
                title = title.value,
                content = content.value,
                gwangsan = gwangsan.value.toIntOrNull() ?: 0,
                imageIds = imageIds.value.map { it.toIntOrNull() ?: 0 }
            )
        )
            .asResult()
            .collectLatest { result ->
            when (result) {
                is com.school_of_company.result.Result.Success -> _postUiState.value = PostUiState.Success
                is com.school_of_company.result.Result.Error -> {
                    _postUiState.value = PostUiState.Error(result.exception)
                    result.exception.errorHandling(
                        badRequestAction = { PostUiState.BadRequest },
                        notFoundAction = { PostUiState.NotFound },
                        conflictAction = { PostUiState.Conflict }
                    )
                }
                is com.school_of_company.result.Result.Loading -> _postUiState.value = PostUiState.Loading
            }
        }
    }

    internal fun modifyPost() = viewModelScope.launch {
        _postUiState.value = PostUiState.Loading
        postRepository.modifyPostInformation(
            type = type.value,
            mode = mode.value,
            request = PostWriteRequest(
                type = type.value,
                mode = mode.value,
                title = title.value,
                content = content.value,
                gwangsan = gwangsan.value.toIntOrNull() ?: 0,
                imageIds = imageIds.value.map { it.toIntOrNull() ?: 0 }
            )
        ).asResult().collectLatest { result ->
            when (result) {
                is com.school_of_company.result.Result.Success -> _postUiState.value = PostUiState.Success
                is com.school_of_company.result.Result.Error -> _postUiState.value = PostUiState.Error(result.exception)
                is com.school_of_company.result.Result.Loading -> _postUiState.value = PostUiState.Loading
            }
        }
    }

    internal fun onTitleChange(value: String) {
        savedStateHandle[TITLE] = value
    }

    internal fun onContentChange(value: String) {
        savedStateHandle[CONTENT] = value
    }

    internal fun onGwangsanChange(value: String) {
        savedStateHandle[GWANGSAN] = value
    }

    internal fun onImageIdsChange(value: List<String>) {
        savedStateHandle[IMAGE_IDS] = value
    }

    internal fun onTypeChange(value: Type) {
        savedStateHandle[TYPE] = value
    }

    internal fun onModeChange(value: Mode) {
        savedStateHandle[MODE] = value
    }
}