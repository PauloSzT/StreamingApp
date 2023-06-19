package com.example.streaming.ui.notification

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaMetadataRetriever.METADATA_KEY_DURATION
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaNotification
import com.example.streaming.MainActivity
import com.example.streaming.R
import com.example.streaming.ui.notification.NotificationMediaService.Companion.NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.media.app.NotificationCompat as MediaNotificationCompat

@AndroidEntryPoint
class CustomMediaService : Service() {

    @Inject
    lateinit var musicPlayer: ExoPlayer

    @Inject
    lateinit var context: Application

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val action = intent?.getStringExtra("action")
        val songName = intent?.getStringExtra("songName") ?: ""
        val author = intent?.getStringExtra("author") ?: ""
        when (action) {
            "play" -> {
                if (musicPlayer.isPlaying) {
                    musicPlayer.pause()
                } else {
                    if (musicPlayer.currentPosition >= musicPlayer.duration.times(1000)) {
                        musicPlayer.seekToDefaultPosition()
                    }
                    musicPlayer.play()
                }
            }
        }
        try {
            notificationUpdate(songName, author)
            setNotification(0, songName, author)
        } catch (e: Exception) {
            Log.wtf("Exception", "$e")
        }
        return START_STICKY
    }

    private fun notificationUpdate(songName: String, author: String) {
        coroutineScope.launch {
            while (true) {
                delay(100)
                setNotification(musicPlayer.currentPosition.toInt(), songName, author)
            }
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun setNotification(progress: Int, songName: String, author: String) {

        val playOrPauseIntent = Intent(this, NotificationMediaService::class.java).also {
            it.putExtra("action", "play")
        }

        val playOrPausePendingIntent: PendingIntent = PendingIntent.getService(
            this@CustomMediaService,
            11,
            playOrPauseIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val mediaPendingIntent =
            PendingIntent.getActivity(
                context, 0, Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_play_square)
            .setProgress(musicPlayer.duration.toInt(), musicPlayer.currentPosition.toInt(), false)
            .setContentIntent(mediaPendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(
                if (musicPlayer.isPlaying) {
                    R.drawable.ic_pause_circle
                } else {
                    R.drawable.ic_play_arrow
                },
                if (musicPlayer.isPlaying) {
                    "⏸️"
                } else {
                    "▶️"
                }, playOrPausePendingIntent
            )
            .setOngoing(false)
            .setContentTitle("$songName - $author")
            .build()
        startForeground(1, notification)
    }
}
