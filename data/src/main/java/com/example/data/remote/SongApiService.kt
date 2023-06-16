package com.example.data.remote

import com.example.data.models.RemoteSearchResult
import com.example.data.models.RemoteSongMedia
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SongApiService {
    @GET()
    suspend fun getSongById(
        @Url url: String
    ): Response<RemoteSongMedia?>

    @GET("/apiv2/search/text")
    suspend fun getSongsBySearch(
        @Query("query") searchQuery: String,
        @Query("page") page: String,
    ): Response<RemoteSearchResult?>
}
