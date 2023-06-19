package com.example.data.di

import com.example.core.repository.remote.RemoteRepository
import com.example.data.repository.remote.RemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRemoteRepositoryWithImpl(
        remoteRepositoryImpl: RemoteRepositoryImpl
    ): RemoteRepository
}
