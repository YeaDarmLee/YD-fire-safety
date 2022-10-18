package com.floortracking.di

import com.floortracking.api.ApiService
import com.floortracking.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(ViewModelComponent::class, SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideMainRepository(apiService: ApiService): MainRepository {
        return MainRepository(apiService = apiService)
    }

}