package com.example.data.repository

import com.example.data.request.LoginRequest
import com.example.data.source.remote.UserRemoteDataSource
import com.example.domain.model.UserInfo
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun login(email: String, password: String): String {
        val token = userRemoteDataSource.login(LoginRequest(email, password))
        return token.accessToken
    }

    override suspend fun getUserInfo(): UserInfo {
        val userInfo = userRemoteDataSource.getUserInfo()
        return UserInfo(
            userInfo.nickname,
            userInfo.email,
            userInfo.major,
            userInfo.studentId,
            userInfo.grade.toString()
        )
    }
}