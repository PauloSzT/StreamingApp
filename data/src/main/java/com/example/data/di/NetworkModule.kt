package com.example.data.di

import com.example.data.remote.AuthorizationInterceptor
import com.example.data.remote.SongApiService
import com.example.data.util.DataConstants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalSerializationApi::class)
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideRetrofit(): SongApiService {
        return Retrofit.Builder()
            .addConverterFactory(jsonConfig.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .client(getClient())
            .build().create()
    }

    @Singleton
    @Provides
    fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val authorizationInterceptor = AuthorizationInterceptor()
        val clientConfig = OkHttpClient.Builder()
        return clientConfig
            .addNetworkInterceptor(interceptor)
            .addInterceptor(authorizationInterceptor)
            .build()
    }
}
