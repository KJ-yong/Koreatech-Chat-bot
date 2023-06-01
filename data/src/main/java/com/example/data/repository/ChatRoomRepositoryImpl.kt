package com.example.data.repository

import com.example.data.entity.ChatEntity
import com.example.data.source.local.ChatRoomLocalDataSource
import com.example.domain.model.Chat
import com.example.domain.repository.ChatRoomRepository
import javax.inject.Inject

class ChatRoomRepositoryImpl @Inject constructor(
    private val chatRoomLocalDataSource: ChatRoomLocalDataSource
): ChatRoomRepository {
    override suspend fun getAll(): List<Chat> {
        val list = mutableListOf<Chat>()
        chatRoomLocalDataSource.getAllChat().forEach {
            list.add(Chat(it.id, it.isUser, it.text))
        }
        list.sortBy { it.id }
        return list
    }

    override suspend fun insertChat(chat: Chat) {
        chatRoomLocalDataSource.insertChat(ChatEntity(chat.id, chat.isUser, chat.text))
    }

    override suspend fun removeAllChat() {
        chatRoomLocalDataSource.removeAllChat()
    }
}