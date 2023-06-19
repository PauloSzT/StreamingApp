package com.example.core.models

data class CoreSearchResult(
    val next: String?,
    val results: List<CoreSearchResultSong>
)

data class CoreSearchResultSong(
    val id: Int,
    val name: String,
    val username: String
)
