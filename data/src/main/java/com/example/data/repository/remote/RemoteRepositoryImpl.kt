package com.example.data.repository.remote

import com.example.core.models.CoreSearchResult
import com.example.core.models.CoreSongMedia
import com.example.core.repository.remote.RemoteRepository
import com.example.data.remote.SongApiService
import com.example.data.util.mapToCoreModel
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: SongApiService) :
    RemoteRepository {
    override suspend fun getSongById(
        songId: Int,
    ): CoreSongMedia? {
        val response = apiService.getSongById("$songId/?format=json")
        return response.body()?.mapToCoreModel()
    }

    override suspend fun getSongsBySearch(
        query: String,
        page: String
    ): CoreSearchResult? {
        val response = apiService.getSongsBySearch(query, page)
        return response.body()?.mapToCoreModel()
    }
}
