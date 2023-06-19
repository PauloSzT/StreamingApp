package com.example.streaming.ui.mediaplayer

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.core.usecases.remote.getsongbyidusecase.GetSongByIdUseCase
import com.example.streaming.ui.notification.CustomMediaService
import com.example.streaming.ui.utils.mapToUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class MediaPlayerScreenViewModel @Inject constructor(
    private val getSongByIdUseCase: GetSongByIdUseCase,
    private val player: ExoPlayer,
    context: Application,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val songId = savedStateHandle.getStateFlow("songId", 0)
    private val isCurrentlyPlaying = MutableStateFlow(false)
    private val seekbarPosition = MutableStateFlow(0f)
    private val songDetail = songId.map { songId ->
        getSongByIdUseCase(songId)?.mapToUiModel()
    }.onEach { songMedia ->
        songMedia?.let {
            val mediaSource = DefaultMediaSourceFactory(context).createMediaSource(
                MediaItem.fromUri(Uri.parse(songMedia.preview))
            )
            player.setMediaSource(mediaSource)
            player.playWhenReady = true
            player.prepare()
            val mediaIntent = Intent(context, CustomMediaService::class.java).also {
                it.putExtra("action", "play")
                it.putExtra("songName", songMedia.name)
                it.putExtra("author", songMedia.username)
            }
            context.startForegroundService(mediaIntent)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val mediaPlayerUiState = MediaPlayerUiState(
        songDetail = songDetail,
        isCurrentlyPlaying = isCurrentlyPlaying,
        seekbarPosition = seekbarPosition,
        playBtnClick = ::playBtnClick,
        skipBtnClick = ::skipBtnClick,
        rewindBtnClick = ::rewindBtnClick
    )

    init {
        viewModelScope.launch {
            while (true) {
                delay(100)
                seekbarPosition.value = player.currentPosition.toFloat()
            }
        }

        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                isCurrentlyPlaying.value = isPlaying
            }
        })
    }

    private fun playBtnClick() {
        if (player.isPlaying) {
            player.pause()
        } else {
            if (player.currentPosition >= (songDetail.value?.duration?.times(1000)?.toLong()
                    ?: 0L)
            ) {
                player.seekToDefaultPosition()
            }
            player.play()
        }
    }

    private fun skipBtnClick() {
        if (player.isPlaying) player.seekTo(player.currentPosition + 15000)
    }

    private fun rewindBtnClick() {
        if (player.isPlaying) player.seekTo(player.currentPosition - 15000)
    }
}
