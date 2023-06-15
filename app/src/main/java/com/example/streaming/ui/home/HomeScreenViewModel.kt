package com.example.streaming.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.usecases.remote.getsongsbysearchusecase.GetSongsBySearchUseCase
import com.example.streaming.ui.utils.UiConstants.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getSongsBySearchUseCase: GetSongsBySearchUseCase
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val searchValue = MutableStateFlow(EMPTY_STRING)
    private val searchValueExecutor = MutableStateFlow(EMPTY_STRING)


    init {
        viewModelScope.launch {
                getSongsBySearchUseCase("music", "1")?.results?.forEach { item ->
                    Log.wtf("paulocode", "$item")
                }
        }
    }

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