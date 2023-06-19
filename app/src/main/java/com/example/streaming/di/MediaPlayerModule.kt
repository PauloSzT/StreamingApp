package com.example.streaming.di

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MediaPlayerModule {

    @Provides
    @Singleton
    fun provideSongPlayer(app: Application): ExoPlayer {
        return ExoPlayer.Builder(app)
            .build()
    }
}
