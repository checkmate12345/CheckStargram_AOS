package com.checkmate.checkstagram.di

import android.content.Context
import com.checkmate.checkstagram.data.respository.StarRepositoryImpl
import com.checkmate.checkstagram.data.service.StarService
import com.checkmate.checkstagram.data.source.StarDataSource
import com.checkmate.checkstagram.domain.repository.StarRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

//private const val BASE_URL = "http://10.0.2.2:8000/"
private const val BASE_URL = "http://192.168.162.80:8000/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkStarModule {

    @Provides
    @Singleton
    @StarRetrofit
    fun provideRetrofitClient(moshi: Moshi, okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideStarService(@StarRetrofit retrofit: Retrofit) : StarService =
        retrofit.create(StarService::class.java)

    @Provides
    @Singleton
    fun provideCheckDataSource(service: StarService) : StarDataSource =
        StarDataSource(service)

    @Provides
    @Singleton
    fun provideCheckRepository(
        dataSource: StarDataSource,
        @ApplicationContext context: Context) : StarRepository =
        StarRepositoryImpl(dataSource, context)
}