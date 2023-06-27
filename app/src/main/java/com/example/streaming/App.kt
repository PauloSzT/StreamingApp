package com.example.streaming

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import com.example.streaming.ui.fetcher.FetcherListener

import com.example.streaming.ui.notification.NotificationMediaService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    var fetcherListener: FetcherListener? = null

    @JvmName("streamingFetcherListener")
    fun setFetcherListener(value: FetcherListener){
        fetcherListener = value
    }

    fun startFetcher(){
        fetcherListener?.let {fetcher ->
            if(fetcher.isIdle()){
                fetcher.startFetching()
            }
        }
    }

    fun stopFetcher(){
        fetcherListener?.let {fetcher ->
            if(!fetcher.isIdle()){
                fetcher.stopFetching()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationMediaService.NOTIFICATION_CHANNEL_ID,
                "Notification_Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
                setSound(null, null)
            }
            channel.description = "Use for StreamingApp"
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
