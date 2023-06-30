package com.example.streaming.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.core.usecases.remote.getsongsbysearchusecase.GetSongsBySearchUseCase
import com.example.streaming.App
import com.example.streaming.ui.utils.UiConstants.EMPTY_STRING
import com.example.streaming.ui.utils.mapToUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getSongsBySearchUseCase: GetSongsBySearchUseCase,
    private val app: Application
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val searchValue = MutableStateFlow(EMPTY_STRING)
    val searchValueExecutor = MutableStateFlow(EMPTY_STRING)
    private val paginatedSongProvider = searchValueExecutor.map { query ->
        if (query.isNotEmpty()) {
            Pager(
                initialKey = null,
                config = PagingConfig(
                    pageSize = 50,
                    enablePlaceholders = false,
                    prefetchDistance = 1
                ),
                pagingSourceFactory = {
                    SongPagingSource({
                        isLoading.value = false
                        (app as App).stopFetcher()
                    }) { page ->
                        getSongsBySearchUseCase(
                            query,
                            page.toString()
                        )?.mapToUiModel()
                    }
                }
            ).flow
        } else {
            null
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val homeScreenUiState = HomeScreenUiState(
        searchValue = searchValue,
        isLoading = isLoading,
        paginatedSongProvider = paginatedSongProvider,
        onQueryChange = ::onQueryChange,
        onImeActionClick = ::onImeActionClick
    )

    private fun onQueryChange(query: String) {
        searchValue.value = if (query.length == 1) query.trim() else query
    }

    private fun onImeActionClick() {
        (app as App).startFetcher()
        isLoading.value = true
        searchValueExecutor.value = searchValue.value
    }
}
