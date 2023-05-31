package com.example.data.repository

import com.example.data.request.ChatRequest
import com.example.data.request.GetSourceRequest
import com.example.data.request.InitContextRequest
import com.example.data.source.remote.ChatBotRemoteDataSource
import com.example.domain.repository.ChatBotRepository
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val chatBotRemoteDataSource: ChatBotRemoteDataSource
) : ChatBotRepository {
    override suspend fun chat(chat: String, fcmToken: String) {
        chatBotRemoteDataSource.chat(ChatRequest(chat, fcmToken))
    }

    override suspend fun getSource(fcmToken: String) {
        chatBotRemoteDataSource.getSource(GetSourceRequest(fcmToken))
    }

    override suspend fun initContext(fcmToken: String) {
        chatBotRemoteDataSource.initContext(InitContextRequest(fcmToken))
    }
}