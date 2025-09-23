package com.school_of_company.chat.viewmodel.uistate

import androidx.compose.runtime.Stable
import com.school_of_company.chat.ui.model.GetChatResponseUi
import com.school_of_company.chat.ui.model.TradeProductUi

@Stable
sealed interface GetLoadTradUiState {

    @Stable
    data object Loading :GetLoadTradUiState

    @Stable
    data class Success(val data: GetChatResponseUi) : GetLoadTradUiState

    @Stable
    data class Error(val exception: Throwable) : GetLoadTradUiState
}