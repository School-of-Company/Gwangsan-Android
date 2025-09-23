package com.school_of_company.chat.viewmodel.uistate

import androidx.compose.runtime.Stable
import com.school_of_company.chat.ui.model.GetChatRoomResponseUi
import kotlinx.collections.immutable.PersistentList

@Stable
sealed interface TradeReservationUiState {

    @Stable
    data object Loading : TradeReservationUiState

    @Stable
    data object  BadRequest : TradeReservationUiState

    @Stable
    data object Unauthorized : TradeReservationUiState

    @Stable
    data object  NotFound : TradeReservationUiState

    @Stable
    data object  Conflict : TradeReservationUiState

    @Stable
    data object  Success : TradeReservationUiState

    @Stable
    data class Error(val exception: Throwable) : TradeReservationUiState
}