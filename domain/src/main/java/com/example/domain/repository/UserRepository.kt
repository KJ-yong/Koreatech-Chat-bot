package com.example.domain.repository

import com.example.domain.model.UserInfo

interface UserRepository {
    suspend fun login(email: String, password: String): String
    suspend fun getUserInfo(): UserInfo
}