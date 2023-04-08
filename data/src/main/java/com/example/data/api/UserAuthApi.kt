package com.example.data.api

import com.example.data.constant.URLConstant
import com.example.data.response.UserInfoResponse
import retrofit2.http.GET

interface UserAuthApi {
    @GET(URLConstant.USER.ME)
    suspend fun getUserInfo(): UserInfoResponse
}