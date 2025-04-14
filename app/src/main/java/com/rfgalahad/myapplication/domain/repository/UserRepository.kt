package com.rfgalahad.myapplication.domain.repository

import com.rfgalahad.myapplication.data.local.UserModel

interface UserRepository {
    suspend fun registerUser(user: UserModel)
    suspend fun loginUser(email: String, password: String): UserModel?
    suspend fun getUser(email: String): UserModel?
}