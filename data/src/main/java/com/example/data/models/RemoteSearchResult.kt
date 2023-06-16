package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RemoteSearchResult(
    val next: String?,
    val results: List<RemoteSearchResultSong?>
)

@Serializable
data class RemoteSearchResultSong(
    val id: Int,
    val name: String,
    val username: String
)
