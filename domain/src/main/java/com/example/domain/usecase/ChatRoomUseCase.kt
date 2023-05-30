package com.example.domain.usecase

import com.example.domain.model.Chat
import com.example.domain.repository.ChatRoomRepository
import javax.inject.Inject

class ChatRoomUseCase @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository
) {
    suspend fun getAllChat() = chatRoomRepository.getAll()
    suspend fun insertChat(chat: Chat) = chatRoomRepository.insertChat(chat)
}