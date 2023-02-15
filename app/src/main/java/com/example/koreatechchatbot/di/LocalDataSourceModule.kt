package com.example.koreatechchatbot.di

import android.content.Context
import com.example.data.source.local.TokenLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    @Provides
    @Singleton
    fun provideTokenLocalDataSource(
        @ApplicationContext applicationContext: Context
    ) = TokenLocalDataSource(applicationContext)
}