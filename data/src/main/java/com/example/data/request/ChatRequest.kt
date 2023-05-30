package com.example.data.request

import com.google.gson.annotations.SerializedName

data class ChatRequest(
    @SerializedName("question")
    val question: String,
    @SerializedName("fcmToken")
    val fcmToken: String
)
