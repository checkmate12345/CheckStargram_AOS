package com.checkmate.checkstagram.di

import javax.inject.Qualifier

// CheckRetrofit.kt
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CheckRetrofit

// StarRetrofit.kt
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StarRetrofit
