package com.example.data.util

import com.example.core.models.CoreSearchResult
import com.example.core.models.CoreSearchResultSong
import com.example.core.models.CoreSongMedia
import com.example.data.models.RemoteSearchResult
import com.example.data.models.RemoteSearchResultSong
import com.example.data.models.RemoteSongMedia

fun RemoteSongMedia.mapToCoreModel(): CoreSongMedia = CoreSongMedia(
    id = id,
    name = name.split(".").first(),
    duration = duration,
    username = username,
    previews = previews.previewHqMp3,
    images = images.remoteImageWaveFormM
)

fun RemoteSearchResult.mapToCoreModel(): CoreSearchResult = CoreSearchResult(
    next = next,
    results = results.mapNotNull { item -> item?.mapToCoreModel() }
)

fun RemoteSearchResultSong.mapToCoreModel(): CoreSearchResultSong = CoreSearchResultSong(
    id = id,
    name = name.split(".").first(),
    username = username
)
