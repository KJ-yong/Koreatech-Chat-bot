package com.example.data.request

import com.google.gson.annotations.SerializedName

data class InitContextRequest(
    @SerializedName("fcmToken")
    val fcmToken: String
)