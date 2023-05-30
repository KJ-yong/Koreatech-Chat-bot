package com.example.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dao.ChatRoomDao
import com.example.data.entity.ChatEntity

@Database(entities = [ChatEntity::class], version = 1)
abstract class ChatRoomDatabase : RoomDatabase() {
    abstract fun dao(): ChatRoomDao
}