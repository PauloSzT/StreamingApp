package com.example.data.usecases.getsongbyidusecaseimpl

import com.example.core.models.CoreSongMedia
import com.example.core.repository.remote.RemoteRepository
import com.example.core.usecases.remote.getsongbyidusecase.GetSongByIdUseCase
import javax.inject.Inject

class GetSongByIdUseCaseImpl @Inject constructor(private val remoteRepository: RemoteRepository): GetSongByIdUseCase {
    override suspend operator fun invoke(
        songId: Int
    ): CoreSongMedia? = remoteRepository.getSongById(songId)
}
