package com.example.domain.usecase

import com.example.domain.model.UserInfo
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<UserInfo> {
        return kotlin.runCatching {
            userRepository.getUserInfo()
        }
    }
}