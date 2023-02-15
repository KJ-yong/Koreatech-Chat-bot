package com.example.data.constant

object URLConstant {
    const val BASE_URL = "http://129.154.196.34:8080"

    object USER {
        const val USER = "users"
        const val ME = "$USER/v1/me"
        const val LOGIN = "$USER/v1/login"
    }

    object ChatBot {
        const val CHAT = "chat/v1/chat"
    }
}