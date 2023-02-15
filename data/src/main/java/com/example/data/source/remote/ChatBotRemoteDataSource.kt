package com.example.data.source.remote

import com.example.data.api.ChatBotApi
import com.example.data.response.ChatBotResponse
import javax.inject.Inject

class ChatBotRemoteDataSource @Inject constructor(
    private val chatBotApi: ChatBotApi
) {
    suspend fun chat(chat: String): ChatBotResponse = chatBotApi.chat(chat)
}