package com.school_of_company.chat.viewmodel.uistate

import androidx.compose.runtime.Stable
import com.school_of_company.chat.ui.model.ChatMessageUi
import com.school_of_company.chat.ui.model.TradeProductUi
import kotlinx.collections.immutable.PersistentList

@Stable
sealed interface TradeUiState {

    @Stable
    data object Loading :TradeUiState

    @Stable
    data class Success(val data: TradeProductUi) : TradeUiState

    @Stable
    data class Error(val exception: Throwable) : TradeUiState
}