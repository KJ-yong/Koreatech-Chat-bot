package com.example.domain.repository


interface ChatBotRepository {
    suspend fun chat(chat: String, fcmToken: String)
    suspend fun getSource(fcmToken: String)
    suspend fun initContext(fcmToken: String)
}