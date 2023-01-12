package com.example.koreatechchatbot.ui.chat

sealed class Chat {
    data class BySelf(val content: String) : Chat()
    data class ByBotOnlyText(val content: String) : Chat()
}
