package com.example.data.request

import com.google.gson.annotations.SerializedName

data class GetSourceRequest(
    @SerializedName("fcmToken")
    val fcmToken: String
)