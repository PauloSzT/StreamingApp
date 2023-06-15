package com.example.core.usecases.remote.getsongbyidusecase

import com.example.core.models.CoreSongMedia

interface GetSongByIdUseCase {
    suspend operator fun invoke(
        songId: Int
    ): CoreSongMedia?
}
