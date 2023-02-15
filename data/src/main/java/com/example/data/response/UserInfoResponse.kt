package com.example.data.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("major")
    val major: String,
    @SerializedName("studentId")
    val studentId: String,
    @SerializedName("grade")
    val grade: Int
)