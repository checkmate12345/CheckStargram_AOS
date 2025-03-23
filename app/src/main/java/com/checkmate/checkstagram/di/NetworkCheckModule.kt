package com.checkmate.checkstagram.di

import com.checkmate.checkstagram.data.respository.CheckRepositoryImpl
import com.checkmate.checkstagram.data.service.CheckService
import com.checkmate.checkstagram.data.source.CheckDataSource
import com.checkmate.checkstagram.domain.repository.CheckRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "http://13.209.227.9:8080"

@Module
@InstallIn(SingletonComponent::class)
object NetworkCheckModule {

    @Provides
    @Singleton
    @CheckRetrofit
    fun provideRetrofitClient(moshi: Moshi, okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // ★ URL + 파라미터 + 응답까지 모두 출력
        }

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)   // 연결 시도 시간
            .readTimeout(60, TimeUnit.SECONDS)      // 서버 응답 대기 시간
            .writeTimeout(60, TimeUnit.SECONDS)     // 서버로 데이터 전송 대기 시간
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideCheckService(@CheckRetrofit retrofit: Retrofit) : CheckService =
        retrofit.create(CheckService::class.java)

    @Provides
    @Singleton
    fun provideCheckDataSource(service: CheckService) : CheckDataSource =
        CheckDataSource(service)

    @Provides
    @Singleton
    fun provideCheckRepository(dataSource: CheckDataSource) : CheckRepository =
        CheckRepositoryImpl(dataSource)
}