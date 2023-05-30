package com.example.koreatechchatbot.di

import android.content.Context
import androidx.room.Room
import com.example.data.source.local.ChatRoomDatabase
import com.example.data.source.local.ChatRoomLocalDataSource
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

    @Provides
    @Singleton
    fun provideChatRoomDatabase(
        @ApplicationContext applicationContext: Context
    ) = Room.databaseBuilder(applicationContext, ChatRoomDatabase::class.java, "chatroom database").build()

    @Provides
    @Singleton
    fun provideChatRoomLocalDataSource(
        chatRoomDatabase: ChatRoomDatabase
    ) = ChatRoomLocalDataSource(chatRoomDatabase)
}