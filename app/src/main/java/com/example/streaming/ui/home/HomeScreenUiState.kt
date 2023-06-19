package com.example.streaming.ui.home

import androidx.paging.PagingData
import com.example.streaming.ui.models.UiSearchResultSong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class HomeScreenUiState(
    val searchValue: StateFlow<String>,
    val isLoading: StateFlow<Boolean>,
    val paginatedSongProvider: StateFlow<Flow<PagingData<UiSearchResultSong>>?>,
    val onQueryChange: (String) -> Unit,
    val onImeActionClick: () -> Unit,
)
