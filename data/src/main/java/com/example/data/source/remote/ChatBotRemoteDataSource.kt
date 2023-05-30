package com.example.data.source.remote

import com.example.data.api.ChatBotApi
import com.example.data.request.ChatRequest
import javax.inject.Inject

class ChatBotRemoteDataSource @Inject constructor(
    private val chatBotApi: ChatBotApi
) {
    suspend fun chat(chatRequest: ChatRequest) {
        chatBotApi.chat(chatRequest)
    }
}