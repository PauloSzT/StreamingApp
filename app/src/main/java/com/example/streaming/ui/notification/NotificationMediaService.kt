package com.example.streaming.ui.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationMediaService : Service() {

    @Inject
    lateinit var player: ExoPlayer
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra("action")) {
            "play" -> {
                if (player.isPlaying) {
                    player.pause()
                } else {
                    if (player.currentPosition >= player.duration.times(1000)) {
                        player.seekToDefaultPosition()
                    }
                    player.play()
                }
            }
        }
        return START_STICKY
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "media_service_channel"
    }
}
