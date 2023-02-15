package com.example.data.response

import com.google.gson.annotations.SerializedName

data class ChatBotResponse(
    @SerializedName("answer")
    val answer: String
)