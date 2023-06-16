package com.example.streaming.ui.mediaplayer

import com.example.streaming.ui.models.UiSongMedia
import kotlinx.coroutines.flow.StateFlow

class MediaPlayerUiState(
    val songDetail: StateFlow<UiSongMedia?>,
    val isCurrentlyPlaying: StateFlow<Boolean>,
    val seekbarPosition: StateFlow<Float>,
    val playBtnClick: () -> Unit,
    val skipBtnClick: () -> Unit,
    val rewindBtnClick: () -> Unit
)
