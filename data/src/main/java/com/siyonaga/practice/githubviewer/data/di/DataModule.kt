package com.siyonaga.practice.githubviewer.data.di

import com.siyonaga.practice.githubviewer.data.GithubRepositoryApiService
import com.siyonaga.practice.githubviewer.data.GithubRepositoryRepositoryImpl
import com.siyonaga.practice.githubviewer.repository.GithubRepositoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    companion object {
        @Provides
        @Singleton
        fun provideGithubRepositoryApiService(): GithubRepositoryApiService {
            val retrofitJson = Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }

            return Retrofit
                .Builder()
                .addConverterFactory(retrofitJson.asConverterFactory("application/json".toMediaType()))
                .baseUrl("https://api.github.com")
                .build()
                .create(GithubRepositoryApiService::class.java)
        }
    }

    @Singleton
    @Binds
    fun bindGithubRepositoryRepository(
        impl: GithubRepositoryRepositoryImpl,
    ): GithubRepositoryRepository
}
