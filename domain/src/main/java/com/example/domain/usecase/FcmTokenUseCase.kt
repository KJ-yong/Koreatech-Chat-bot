package com.example.domain.usecase

import com.example.domain.repository.TokenRepository
import javax.inject.Inject

class FcmTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend fun saveToken(token: String) {
        tokenRepository.saveFcmToken(token)
    }

    suspend fun getToken(): String? {
        return tokenRepository.getFcmToken()
    }
}