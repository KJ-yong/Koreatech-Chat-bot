package com.example.data.source.remote

import com.example.data.api.UserAuthApi
import com.example.data.api.UserNoAuthApi
import com.example.data.request.LoginRequest
import com.example.data.response.LoginResponse
import com.example.data.response.UserInfoResponse
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userAuthApi: UserAuthApi,
    private val userNoAuthApi: UserNoAuthApi
) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse = userNoAuthApi.login(loginRequest)
    suspend fun getUserInfo(): UserInfoResponse = userAuthApi.getUserInfo()
}