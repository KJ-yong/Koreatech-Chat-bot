package com.example.koreatechchatbot.di

import com.example.data.repository.ChatBotRepositoryImpl
import com.example.data.repository.TokenRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.data.source.local.TokenLocalDataSource
import com.example.data.source.remote.ChatBotRemoteDataSource
import com.example.data.source.remote.UserRemoteDataSource
import com.example.domain.repository.ChatBotRepository
import com.example.domain.repository.TokenRepository
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository = UserRepositoryImpl(userRemoteDataSource)

    @Provides
    @Singleton
    fun provideChatBotRepository(
        chatBotRemoteDataSource: ChatBotRemoteDataSource
    ): ChatBotRepository = ChatBotRepositoryImpl(chatBotRemoteDataSource)

    @Provides
    @Singleton
    fun provideTokenRepository(
        tokenLocalDataSource: TokenLocalDataSource
    ): TokenRepository = TokenRepositoryImpl(tokenLocalDataSource)
}