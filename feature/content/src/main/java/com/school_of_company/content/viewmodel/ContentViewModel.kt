package com.school_of_company.content.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.content.viewmodel.uistate.GetSpecificPostUiState
import com.school_of_company.content.viewmodel.uistate.ReportPostUiState
import com.school_of_company.content.viewmodel.uistate.ReviewPostUiState
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.data.repository.report.ReportRepository
import com.school_of_company.data.repository.review.ReviewRepository
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
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _getSpecificPostUiState =
        MutableStateFlow<GetSpecificPostUiState>(GetSpecificPostUiState.Loading)
    internal val getSpecificPostUiState = _getSpecificPostUiState.asStateFlow()

    private val _reportPostUiState = MutableStateFlow<ReportPostUiState>(ReportPostUiState.Loading)
    internal val reportPostUiState = _reportPostUiState.asStateFlow()

    private val _reviewPostUiState = MutableStateFlow<ReviewPostUiState>(ReviewPostUiState.Loading)
    internal val reviewPostUiState = _reviewPostUiState.asStateFlow()

    internal fun getSpecificPost(postId: Long) = viewModelScope.launch {
        _getSpecificPostUiState.value = GetSpecificPostUiState.Loading
        postRepository.getSpecificInformation(postId = postId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getSpecificPostUiState.value =
                        GetSpecificPostUiState.Loading

                    is Result.Success -> _getSpecificPostUiState.value =
                        GetSpecificPostUiState.Success(result.data)

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
}