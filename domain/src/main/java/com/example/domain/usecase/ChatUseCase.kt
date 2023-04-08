package com.example.domain.usecase

import com.example.domain.repository.ChatBotRepository
import javax.inject.Inject

class ChatUseCase @Inject constructor(
    private val chatBotRepository: ChatBotRepository
) {
    suspend operator fun invoke(chat: String): Result<String> {
        return kotlin.runCatching {
            chatBotRepository.chat(chat)
        }
    }
}