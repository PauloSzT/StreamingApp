package com.example.data.usecases.getsongsbysearchusecaseimpl

import com.example.core.models.CoreSearchResult
import com.example.core.repository.remote.RemoteRepository
import com.example.core.usecases.remote.getsongsbysearchusecase.GetSongsBySearchUseCase
import javax.inject.Inject

class GetSongsBySearchUseCaseImpl @Inject constructor(private val remoteRepository: RemoteRepository) :
    GetSongsBySearchUseCase {
    override suspend fun invoke(query: String, page: String): CoreSearchResult? =
        remoteRepository.getSongsBySearch(query, page)
}
