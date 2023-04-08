package com.example.data.repository

import com.example.data.source.remote.ChatBotRemoteDataSource
import com.example.domain.repository.ChatBotRepository
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val chatBotRemoteDataSource: ChatBotRemoteDataSource
) : ChatBotRepository {
    override suspend fun chat(chat: String): String {
        return chatBotRemoteDataSource.chatFlow(chat).answer
    }
}