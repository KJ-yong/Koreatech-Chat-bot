package com.example.domain.repository

import com.example.domain.model.Chat

interface ChatRoomRepository {
    suspend fun getAll(): List<Chat>
    suspend fun insertChat(chat: Chat)
    suspend fun removeAllChat()
}