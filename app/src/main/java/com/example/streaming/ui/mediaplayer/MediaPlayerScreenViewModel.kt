package com.example.streaming.ui.mediaplayer

import androidx.lifecycle.ViewModel
import com.example.streaming.ui.models.UiSongMedia
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MediaPlayerScreenViewModel@Inject constructor(): ViewModel() {
    private val songMediaItem: MutableStateFlow<UiSongMedia?> = MutableStateFlow(null)

    val mediaPlayerUiState = MediaPlayerUiState(

    )

}
