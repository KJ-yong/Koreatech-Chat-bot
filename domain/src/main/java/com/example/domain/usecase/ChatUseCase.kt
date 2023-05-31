package com.example.domain.usecase

import com.example.domain.repository.ChatBotRepository
import javax.inject.Inject

class ChatUseCase @Inject constructor(
    private val chatBotRepository: ChatBotRepository
) {
    suspend operator fun invoke(chat: String, fcmToken: String): Result<Unit> {
        return kotlin.runCatching {
            chatBotRepository.chat(chat, fcmToken)
        }
    }

    suspend fun getSource(fcmToken: String): Result<Unit> {
        return kotlin.runCatching {
            chatBotRepository.getSource(fcmToken)
        }
    }

    suspend fun initContext(fcmToken: String): Result<Unit> {
        return kotlin.runCatching {
            chatBotRepository.initContext(fcmToken)
        }
    }
}