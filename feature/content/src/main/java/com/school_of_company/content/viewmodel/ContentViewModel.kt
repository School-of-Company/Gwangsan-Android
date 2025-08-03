package com.school_of_company.content.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.content.ui.mapper.toUi
import com.school_of_company.content.until.getMultipartFile
import com.school_of_company.content.viewmodel.uistate.DeletePostUiState
import com.school_of_company.content.viewmodel.uistate.GetSpecificPostUiState
import com.school_of_company.content.viewmodel.uistate.ImageUpLoadUiState
import com.school_of_company.content.viewmodel.uistate.ReportPostUiState
import com.school_of_company.content.viewmodel.uistate.ReviewPostUiState
import com.school_of_company.content.viewmodel.uistate.TransactionCompleteUiState
import com.school_of_company.data.repository.image.ImageRepository
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.data.repository.report.ReportRepository
import com.school_of_company.data.repository.review.ReviewRepository
import com.school_of_company.model.post.request.TransactionCompleteRequestModel
import com.school_of_company.model.report.request.ReportRequestModel
import com.school_of_company.model.review.request.ReviewRequestModel
import com.school_of_company.network.errorHandling
import com.school_of_company.result.Result
import com.school_of_company.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val reportRepository: ReportRepository,
    private val imageRepository: ImageRepository,
    private val reviewRepository: ReviewRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val IMAGE_IDS = "imageIds"
    }

    private val _existingImageUrls = MutableStateFlow<List<String>>(emptyList())
    internal val existingImageUrls = _existingImageUrls.asStateFlow()

    private val _selectedImages = MutableStateFlow<List<Uri>>(emptyList())
    internal val selectedImages = _selectedImages.asStateFlow()

    fun addImage(uri: Uri) {
        _selectedImages.value += uri
    }

    private val _imageUpLoadUiState = MutableStateFlow<ImageUpLoadUiState>(ImageUpLoadUiState.Loading)
    internal val imageUpLoadUiState = _imageUpLoadUiState.asStateFlow()

    private val _deletePostUiState = MutableStateFlow<DeletePostUiState>(DeletePostUiState.Loading)
    internal val deletePostUiState = _deletePostUiState.asStateFlow()

    private val _getSpecificPostUiState = MutableStateFlow<GetSpecificPostUiState>(GetSpecificPostUiState.Loading)
    internal val getSpecificPostUiState = _getSpecificPostUiState.asStateFlow()

    private val _reportPostUiState = MutableStateFlow<ReportPostUiState>(ReportPostUiState.Loading)
    internal val reportPostUiState = _reportPostUiState.asStateFlow()

    private val _transactionCompleteUiState = MutableStateFlow<TransactionCompleteUiState>(TransactionCompleteUiState.Loading)
    internal val transactionCompleteUiState = _transactionCompleteUiState.asStateFlow()

    private val _reviewPostUiState = MutableStateFlow<ReviewPostUiState>(ReviewPostUiState.Loading)
    internal val reviewPostUiState = _reviewPostUiState.asStateFlow()

    internal val imageIds = savedStateHandle.getStateFlow(IMAGE_IDS, emptyList<Long>())

    internal fun deletePost(postId: Long) = viewModelScope.launch {
        postRepository.deletePostInformation(postId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _deletePostUiState.value = DeletePostUiState.Loading

                    is Result.Success -> _deletePostUiState.value = DeletePostUiState.Success

                    is Result.Error -> _deletePostUiState.value = DeletePostUiState.Error(result.exception)
                }
            }
    }

    internal fun getSpecificPost(postId: Long) = viewModelScope.launch {
        _getSpecificPostUiState.value = GetSpecificPostUiState.Loading
        postRepository.getSpecificInformation(postId = postId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getSpecificPostUiState.value =
                        GetSpecificPostUiState.Loading

                    is Result.Success -> _getSpecificPostUiState.value =
                        GetSpecificPostUiState.Success(result.data.toUi())

                    is Result.Error -> _getSpecificPostUiState.value =
                        GetSpecificPostUiState.Error(result.exception)
                }
            }
    }

    internal fun reportPost(body: ReportRequestModel) = viewModelScope.launch {
        _reportPostUiState.value = ReportPostUiState.Loading
        reportRepository.report(body = body)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _reportPostUiState.value = ReportPostUiState.Loading
                    is Result.Success -> _reportPostUiState.value = ReportPostUiState.Success
                    is Result.Error -> _reportPostUiState.value =
                        ReportPostUiState.Error(result.exception)
                }
            }
    }

    internal fun reviewPost(body: ReviewRequestModel) = viewModelScope.launch {
        _reviewPostUiState.value = ReviewPostUiState.Loading
        reviewRepository.postReview(body = body)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _reviewPostUiState.value = ReviewPostUiState.Loading
                    is Result.Success -> _reviewPostUiState.value = ReviewPostUiState.Success
                    is Result.Error -> _reviewPostUiState.value = ReviewPostUiState.Error(result.exception)
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

    fun onImageIdAdded(id: Long) {
        val currentList = imageIds.value.toMutableList()
        currentList.add(id)
        savedStateHandle[IMAGE_IDS] = currentList
    }

    internal fun removeNewImage(index: Int) {
        val currentImages = _selectedImages.value.toMutableList()
        if (index < currentImages.size) {
            currentImages.removeAt(index)
            _selectedImages.value = currentImages
        }

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

    internal fun onImageIdsChange(value: List<Long>) {
        savedStateHandle[IMAGE_IDS] = value
    }
}