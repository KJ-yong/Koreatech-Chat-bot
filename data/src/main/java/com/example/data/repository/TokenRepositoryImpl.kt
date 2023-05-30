package com.example.data.repository

import android.util.Log
import com.example.data.source.local.TokenLocalDataSource
import com.example.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {
    override suspend fun saveAccessToken(token: String) {
        Log.e("test_token","access token : $token")
        tokenLocalDataSource.saveAccessToken(token)
    }

    override suspend fun getAccessToken(): String? {
        return tokenLocalDataSource.getAccessToken()
    }

    override suspend fun saveFcmToken(token: String) {
        tokenLocalDataSource.saveFcmToken(token)
    }

    override suspend fun getFcmToken(): String? {
        return tokenLocalDataSource.getFcmToken()
    }
}