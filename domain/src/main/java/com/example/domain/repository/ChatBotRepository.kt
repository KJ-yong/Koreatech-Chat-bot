package com.example.domain.repository


interface ChatBotRepository {
    suspend fun chat(chat: String): String
}