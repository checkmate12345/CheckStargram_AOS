package com.checkmate.checkstagram.di

import com.checkmate.checkstagram.data.respository.MediaRepositoryImpl
import com.checkmate.checkstagram.domain.repository.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMediaRepository() : MediaRepository = MediaRepositoryImpl()

}