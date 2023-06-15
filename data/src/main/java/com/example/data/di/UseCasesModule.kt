package com.example.data.di

import com.example.core.usecases.remote.getsongbyidusecase.GetSongByIdUseCase
import com.example.core.usecases.remote.getsongsbysearchusecase.GetSongsBySearchUseCase
import com.example.data.usecases.getsongbyidusecaseimpl.GetSongByIdUseCaseImpl
import com.example.data.usecases.getsongsbysearchusecaseimpl.GetSongsBySearchUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {
    @Binds
    abstract fun bindsGetSongsBySearchUseCaseWithImpl(
        getSongsBySearchUseCaseImpl: GetSongsBySearchUseCaseImpl
    ): GetSongsBySearchUseCase

    @Binds
    abstract fun bindsGetSongByIdUseCaseWithImpl(
        getSongByIdUseCaseImpl: GetSongByIdUseCaseImpl
    ): GetSongByIdUseCase
}
