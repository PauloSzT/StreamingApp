package com.example.streaming

import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val musicPlayer : ExoPlayer) : ViewModel() {

    fun stopMusicPlay(){
        musicPlayer.stop()
        musicPlayer.release()
    }
}
