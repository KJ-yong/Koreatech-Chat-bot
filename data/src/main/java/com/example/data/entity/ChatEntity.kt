package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity(
    @PrimaryKey val id: Int,
    val isUser: Boolean,
    val text: String
)