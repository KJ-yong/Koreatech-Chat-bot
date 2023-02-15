package com.example.data.api

import com.example.data.constant.URLConstant
import com.example.data.request.LoginRequest
import com.example.data.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserNoAuthApi {
    @POST(URLConstant.USER.LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}