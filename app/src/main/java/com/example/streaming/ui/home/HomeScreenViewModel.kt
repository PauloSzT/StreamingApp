package com.example.streaming.ui.home

import androidx.lifecycle.ViewModel
import com.example.streaming.ui.utils.UiConstants.EMPTY_STRING
import kotlinx.coroutines.flow.MutableStateFlow

class HomeScreenViewModel() : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val searchValue = MutableStateFlow(EMPTY_STRING)
    private val searchValueExecutor = MutableStateFlow(EMPTY_STRING)

    val homeScreenUiState = HomeScreenUiState(
        searchValue = searchValue,
        isLoading = isLoading,
        onQueryChange = ::onQueryChange,
        onImeActionClick = ::onImeActionClick
    )

    private fun onQueryChange(query: String) {
        searchValue.value = if (query.length == 1) query.trim() else query
    }

    private fun onImeActionClick() {
        isLoading.value = true
        searchValueExecutor.value = searchValue.value
    }
}