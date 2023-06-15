package com.example.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteSongMedia(
    val id: Int,
    val name: String,
    val duration: Float,
    val previews: RemoteSongPreview,
    val images: RemoteSongImage
)

@Serializable
data class RemoteSongPreview(
    @SerialName("preview-hq-mp3")
    val previewHqMp3: String
)

@Serializable
data class RemoteSongImage(
    @SerialName("waveform_m")
    val remoteImageWaveFormM: String
)
