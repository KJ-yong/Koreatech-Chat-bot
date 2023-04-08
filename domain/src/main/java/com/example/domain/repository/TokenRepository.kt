package com.example.domain.repository

interface TokenRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String?
}