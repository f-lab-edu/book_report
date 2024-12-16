package com.twitty.feature.home

import com.twitty.model.HomeResource

sealed interface HomeScreenUiState {
    data object Loading : HomeScreenUiState
    data class Success(
        val homeResource: HomeResource,
        val isLoading: Boolean = false
    ) : HomeScreenUiState
    data class Error(val errorMessage: String) : HomeScreenUiState
}
