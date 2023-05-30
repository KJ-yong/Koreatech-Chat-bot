package com.example.data.api

import com.example.data.constant.URLConstant
import com.example.data.request.ChatRequest
import com.example.data.response.ChatBotResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatBotApi {
    @POST(URLConstant.ChatBot.CHAT)
    suspend fun chat(@Body chatRequest: ChatRequest)
}