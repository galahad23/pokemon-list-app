package com.rfgalahad.myapplication.data.repository

import com.rfgalahad.myapplication.data.local.UserDao
import com.rfgalahad.myapplication.data.local.UserModel
import com.rfgalahad.myapplication.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {
    override suspend fun registerUser(user: UserModel): Boolean {
        val existingUser = userDao.getUserByEmail(user.email)
        return if (existingUser == null) {
            userDao.insertUser(user)
            true
        } else {
            false
        }
    }

    override suspend fun loginUser(email: String, password: String): UserModel? {
        return userDao.login(email, password)
    }

    override suspend fun getUser(email: String): UserModel? {
        return userDao.getUserByEmail(email)
    }
}