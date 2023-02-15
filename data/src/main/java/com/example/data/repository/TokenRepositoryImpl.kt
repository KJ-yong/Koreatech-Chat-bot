package com.example.data.repository

import com.example.data.source.local.TokenLocalDataSource
import com.example.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {
    override suspend fun saveAccessToken(token: String) {
        tokenLocalDataSource.saveAccessToken(token)
    }

    override suspend fun getAccessToken(): String? {
        return tokenLocalDataSource.getAccessToken()
    }
}