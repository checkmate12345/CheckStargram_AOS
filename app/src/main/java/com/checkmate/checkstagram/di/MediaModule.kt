package com.checkmate.checkstagram.di

import androidx.activity.result.ActivityResultCaller
import com.checkmate.checkstagram.data.respository.MediaRepository
import com.checkmate.checkstagram.domain.repositoryImpl.MediaRepositoryImpl
import com.checkmate.checkstagram.domain.usecase.PickImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMediaRepository() : MediaRepository = MediaRepositoryImpl()

}