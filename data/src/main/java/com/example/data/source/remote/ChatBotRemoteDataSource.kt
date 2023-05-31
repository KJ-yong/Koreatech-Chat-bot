package com.example.data.source.remote

import com.example.data.api.ChatBotApi
import com.example.data.request.ChatRequest
import com.example.data.request.GetSourceRequest
import com.example.data.request.InitContextRequest
import javax.inject.Inject

class ChatBotRemoteDataSource @Inject constructor(
    private val chatBotApi: ChatBotApi
) {
    suspend fun chat(chatRequest: ChatRequest) {
        chatBotApi.chat(chatRequest)
    }

    suspend fun getSource(getSourceRequest: GetSourceRequest) {
        chatBotApi.getSource(getSourceRequest)
    }

    suspend fun initContext(initContextRequest: InitContextRequest) {
        chatBotApi.initContext(initContextRequest)
    }
}