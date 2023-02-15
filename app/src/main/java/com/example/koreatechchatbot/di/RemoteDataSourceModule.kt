package com.example.koreatechchatbot.di

import com.example.data.api.ChatBotApi
import com.example.data.api.UserAuthApi
import com.example.data.api.UserNoAuthApi
import com.example.data.source.remote.ChatBotRemoteDataSource
import com.example.data.source.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {
    @Provides
    @Singleton
    fun provideUserDataSource(
        userAuthApi: UserAuthApi,
        userNoAuthApi: UserNoAuthApi
    ) = UserRemoteDataSource(userAuthApi, userNoAuthApi)

    @Provides
    @Singleton
    fun provideChatBotDataSource(
        chatBotApi: ChatBotApi
    ) = ChatBotRemoteDataSource(chatBotApi)
}