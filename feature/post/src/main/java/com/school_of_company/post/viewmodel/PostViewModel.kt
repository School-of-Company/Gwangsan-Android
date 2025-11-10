package com.school_of_company.post.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.data.repository.image.ImageRepository
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.errorHandling
import com.school_of_company.result.asResult
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.model.post.request.PostAllRequestModel
import com.school_of_company.post.util.getMultipartFile
import com.school_of_company.post.viewmodel.uiState.ImageUpLoadUiState
import com.school_of_company.post.viewmodel.uiState.ModifyPostUiState
import com.school_of_company.post.viewmodel.uiState.PostUiState
import com.school_of_company.profile.viewmodel.uistate.GetMySpecificInformationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.school_of_company.result.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val imageRepository: ImageRepository,
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

    private val _existingImageUrls = MutableStateFlow<List<String>>(emptyList())
    internal val existingImageUrls = _existingImageUrls.asStateFlow()

    private val _selectedImages = MutableStateFlow<List<Uri>>(emptyList())
    internal val selectedImages = _selectedImages.asStateFlow()

    fun addImage(uri: Uri) {
        _selectedImages.value += uri
    }

    /** 외부에서 쓰는 간단 API (SavedStateHandle 기반 변경을 래핑) */
    fun setType(type: Type) = onTypeChange(type)
    fun setMode(mode: Mode) = onModeChange(mode)

    private val _postUiState = MutableStateFlow<PostUiState>(PostUiState.Loading)
    internal val postUiState = _postUiState.asStateFlow()

    private val _modifyPostUiStat = MutableStateFlow<ModifyPostUiState>(ModifyPostUiState.Loading)
    internal val modifyPostUiStat = _modifyPostUiStat.asStateFlow()

    private val _getMySpecificInformationUiState =
        MutableStateFlow<GetMySpecificInformationUiState>(GetMySpecificInformationUiState.Loading)
    internal val getMySpecificInformationUiState = _getMySpecificInformationUiState.asStateFlow()

    private val _imageUpLoadUiState =
        MutableStateFlow<ImageUpLoadUiState>(ImageUpLoadUiState.Loading)
    internal val imageUpLoadUiState = _imageUpLoadUiState.asStateFlow()

    /** SavedStateHandle - 화면 회전/프로세스 재생성에도 유지 */
    internal val title = savedStateHandle.getStateFlow(TITLE, "")
    internal val content = savedStateHandle.getStateFlow(CONTENT, "")
    internal val gwangsan = savedStateHandle.getStateFlow(GWANGSAN, "")
    internal val imageIds = savedStateHandle.getStateFlow(IMAGE_IDS, emptyList<Long>())
    internal val type = savedStateHandle.getStateFlow(TYPE, Type.OBJECT)
    internal val mode = savedStateHandle.getStateFlow(MODE, Mode.GIVER)

    private val _isEditMode = MutableStateFlow(false)
    val isEditMode: StateFlow<Boolean> = _isEditMode.asStateFlow()

    private val _editPostId = MutableStateFlow<Long?>(null)
    val editPostId: StateFlow<Long?> = _editPostId.asStateFlow()

    internal fun loadPostForEdit(postId: Long) = viewModelScope.launch {
        _postUiState.value = PostUiState.Loading
        _isEditMode.value = true
        _editPostId.value = postId

        postRepository.getSpecificInformation(postId = postId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getMySpecificInformationUiState.value =
                        GetMySpecificInformationUiState.Loading

                    is Result.Success -> {
                        _getMySpecificInformationUiState.value =
                            GetMySpecificInformationUiState.Success(result.data)

                        result.data.let { editData ->
                            onGwangsanChange(editData.gwangsan.toString())
                            onTitleChange(editData.title)
                            onContentChange(editData.content)
                            onImageIdsChange(editData.images.map { it.imageId })

                            _existingImageUrls.value = editData.images.map { it.imageUrl }
                            _selectedImages.value = emptyList()

                            editData.type.let { typeString ->
                                try {
                                    val parsed = Type.valueOf(typeString)
                                    onTypeChange(parsed)
                                } catch (_: IllegalArgumentException) { }
                            }

                            editData.mode.let { modeString ->
                                try {
                                    val parsed = Mode.valueOf(modeString)
                                    onModeChange(parsed)
                                } catch (_: IllegalArgumentException) { }
                            }
                        }
                    }

                    is Result.Error -> _getMySpecificInformationUiState.value =
                        GetMySpecificInformationUiState.Error(result.exception)
                }
            }
    }

    internal fun modifyPost(postId: Long) = viewModelScope.launch {
        if (title.value.isBlank() || content.value.isBlank()) {
            _postUiState.value = PostUiState.Error(IllegalArgumentException("빈 값 존재"))
            return@launch
        }

        _modifyPostUiStat.value = ModifyPostUiState.Loading
        try {
            postRepository.modifyPostInformation(
                postId = postId,
                body = PostAllRequestModel(
                    type = type.value.name,
                    mode = mode.value.name,
                    title = title.value,
                    content = content.value,
                    gwangsan = gwangsan.value.toInt(),
                    imageIds = imageIds.value
                )
            ).asResult().collectLatest { result ->
                when (result) {
                    is Result.Loading -> _modifyPostUiStat.value = ModifyPostUiState.Loading
                    is Result.Success -> {
                        _modifyPostUiStat.value = ModifyPostUiState.Success
                        _selectedImages.value = emptyList()
                        _isEditMode.value = false
                        _editPostId.value = null
                    }
                    is Result.Error -> {
                        if (mode.value == Mode.GIVER && type.value == Type.OBJECT && imageIds.value.isEmpty()) {
                            _modifyPostUiStat.value = ModifyPostUiState.NotFoundImage
                        } else {
                            _modifyPostUiStat.value = ModifyPostUiState.Error(result.exception)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            _modifyPostUiStat.value = ModifyPostUiState.Error(e)
        }
    }

    internal fun writePost() = viewModelScope.launch {
        if (title.value.isBlank() || content.value.isBlank()) {
            _postUiState.value = PostUiState.Error(IllegalArgumentException("빈 값 존재"))
            return@launch
        }

        _postUiState.value = PostUiState.Loading
        postRepository.writePostInformation(
            body = PostAllRequestModel(
                type = type.value.name,
                mode = mode.value.name,
                title = title.value,
                content = content.value,
                gwangsan = gwangsan.value.toInt(),
                imageIds = imageIds.value
            )
        ).asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> _postUiState.value = PostUiState.Success
                is Result.Error -> {
                    result.exception.errorHandling(
                        badRequestAction = { PostUiState.BadRequest },
                        notFoundAction = { PostUiState.NotFound },
                    )
                    if (mode.value == Mode.GIVER && type.value == Type.OBJECT && imageIds.value.isEmpty()) {
                        _postUiState.value = PostUiState.NotFoundImage
                    } else {
                        _postUiState.value = PostUiState.Error(result.exception)
                    }
                }
                is Result.Loading -> _postUiState.value = PostUiState.Loading
            }
        }
    }

    internal suspend fun imageUpLoad(context: Context, image: Uri): Long {
        val multipartFile = getMultipartFile(context, image)
            ?: throw IllegalStateException("이미지 파일 변환 실패")

        var imageId: Long = -1

        imageRepository.imageUpLoad(multipartFile)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _imageUpLoadUiState.value = ImageUpLoadUiState.Loading
                    }
                    is Result.Success -> {
                        _imageUpLoadUiState.value = ImageUpLoadUiState.Success(result.data)
                        imageId = result.data.imageId
                    }
                    is Result.Error -> {
                        _imageUpLoadUiState.value = ImageUpLoadUiState.Error(result.exception)
                        throw result.exception
                    }
                }
            }

        return imageId
    }

    internal fun removeExistingImage(index: Int) {
        val currentUrls = _existingImageUrls.value.toMutableList()
        val currentIds = imageIds.value.toMutableList()

        if (index < currentUrls.size) {
            currentUrls.removeAt(index)
            currentIds.removeAt(index)
            _existingImageUrls.value = currentUrls
            onImageIdsChange(currentIds)
        }
    }

    internal fun removeNewImage(index: Int) {
        val currentImages = _selectedImages.value.toMutableList()
        if (index < currentImages.size) {
            currentImages.removeAt(index)
            _selectedImages.value = currentImages
        }
    }

    fun onImageIdAdded(id: Long) {
        val currentList = imageIds.value.toMutableList()
        currentList.add(id)
        savedStateHandle[IMAGE_IDS] = currentList
    }

    internal fun onTitleChange(value: String) { savedStateHandle[TITLE] = value }
    internal fun onContentChange(value: String) { savedStateHandle[CONTENT] = value }
    internal fun onGwangsanChange(value: String) { savedStateHandle[GWANGSAN] = value }
    internal fun onImageIdsChange(value: List<Long>) { savedStateHandle[IMAGE_IDS] = value }
    internal fun onTypeChange(value: Type) { savedStateHandle[TYPE] = value }
    internal fun onModeChange(value: Mode) { savedStateHandle[MODE] = value }

    fun resetPostState() {
        _postUiState.value = PostUiState.Loading
    }

    fun resetModifyState() {
        _modifyPostUiStat.value = ModifyPostUiState.Loading
    }
}