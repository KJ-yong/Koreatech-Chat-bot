package com.example.koreatechchatbot.di

import android.content.Context
import com.example.data.constant.URLConstant
import com.example.koreatechchatbot.di.qualifier.ServerUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(
    ) = HttpLoggingInterceptor()

    @ServerUrl
    @Provides
    @Singleton
    fun provideServerUrl(
    ) = URLConstant.BASE_URL
}