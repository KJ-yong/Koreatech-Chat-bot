package com.example.domain.usecase

import com.example.domain.repository.TokenRepository
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return kotlin.runCatching {
            val accessToken = userRepository.login(email, password)
            tokenRepository.saveAccessToken(accessToken)
        }
    }
}