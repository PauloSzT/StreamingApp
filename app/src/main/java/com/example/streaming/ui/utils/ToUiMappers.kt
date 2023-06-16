package com.example.streaming.ui.utils

import com.example.core.models.CoreSearchResult
import com.example.core.models.CoreSearchResultSong
import com.example.core.models.CoreSongMedia
import com.example.streaming.ui.models.UiSearchResult
import com.example.streaming.ui.models.UiSearchResultSong
import com.example.streaming.ui.models.UiSongMedia

fun CoreSearchResult.mapToUiModel(): UiSearchResult = UiSearchResult(
    next = next,
    results = results.map { item -> item.mapToUiModel() }
)

fun CoreSearchResultSong.mapToUiModel(): UiSearchResultSong = UiSearchResultSong(
    id = id,
    name = name,
    username = username
)

fun CoreSongMedia.mapToUiModel(): UiSongMedia = UiSongMedia(
    id = id,
    name = name,
    duration = duration,
    username = username,
    preview = previews,
    image = images
)
