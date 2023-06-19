package com.example.core.repository.remote

import com.example.core.models.CoreSearchResult
import com.example.core.models.CoreSongMedia

interface RemoteRepository {

    suspend fun getSongById(
        songId: Int
    ): CoreSongMedia?

    suspend fun getSongsBySearch(
        query: String,
        page: String
    ): CoreSearchResult?
}
