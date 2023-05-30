package com.example.data.source.local

import com.example.data.entity.ChatEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRoomLocalDataSource @Inject constructor(
    private val chatRoomDB: ChatRoomDatabase
) {
    val dao = chatRoomDB.dao()

    suspend fun getAllChat() = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    suspend fun insertChat(chat: ChatEntity) = withContext(Dispatchers.IO){
        dao.insert(chat)
    }
}