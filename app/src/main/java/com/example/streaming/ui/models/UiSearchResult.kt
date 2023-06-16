package com.example.streaming.ui.models

data class UiSearchResult(
    val next: String?,
    val results: List<UiSearchResultSong>
)

data class UiSearchResultSong(
    val id: Int,
    val name: String,
    val username: String
)
