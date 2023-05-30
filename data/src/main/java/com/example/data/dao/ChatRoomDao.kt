package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.entity.ChatEntity

@Dao
interface ChatRoomDao {
    @Query("SELECT * From ChatEntity")
    fun getAll(): List<ChatEntity>

    @Insert
    fun insert(chat: ChatEntity)
}