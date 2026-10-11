package com.siyonaga.practice.githubviewer.detail

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface DetailScreenUiState {
    @Immutable
    data object Loading : DetailScreenUiState

    @Immutable
    data class Success(
        val readmeText: String,
    ) : DetailScreenUiState

    @Immutable
    data class Error(
        val isNotFoundException: Boolean,
    ) : DetailScreenUiState
}
