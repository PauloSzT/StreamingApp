package com.example.core.usecases.remote.getsongsbysearchusecase

import com.example.core.models.CoreSearchResult

interface GetSongsBySearchUseCase {
    suspend operator fun invoke(
        query: String,
        page: String
    ): CoreSearchResult?
}
