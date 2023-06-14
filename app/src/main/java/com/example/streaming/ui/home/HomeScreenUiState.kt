package com.example.streaming.ui.home

import kotlinx.coroutines.flow.StateFlow

class HomeScreenUiState(
    val searchValue: StateFlow<String>,
    val isLoading: StateFlow<Boolean>,
    val onQueryChange: (String) -> Unit,
    val onImeActionClick: () -> Unit
)
