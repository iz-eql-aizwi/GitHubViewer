package com.siyonaga.practice.githubviewer.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.siyonaga.practice.githubviewer.model.Order

@Stable
sealed interface HomeScreenUiState {
    @Immutable
    data object Initial : HomeScreenUiState

    @Immutable
    data class Success(
        val word: String = "android",
        val order: Order = Order.DESC,
    ) : HomeScreenUiState
}
