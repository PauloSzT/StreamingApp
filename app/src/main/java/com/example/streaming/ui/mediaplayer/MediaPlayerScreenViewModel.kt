package com.example.streaming.ui.mediaplayer

import androidx.lifecycle.ViewModel
import com.example.streaming.ui.models.UiSongMedia
import kotlinx.coroutines.flow.MutableStateFlow

class MediaPlayerScreenViewModel(): ViewModel() {
    private val songMediaitem: MutableStateFlow<UiSongMedia?> = MutableStateFlow(null)

//    init {
//        viewModelScope.launch {
//        }
//    }

}
