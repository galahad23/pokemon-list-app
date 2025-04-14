package com.rfgalahad.myapplication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rfgalahad.myapplication.data.repository.UserRepositoryImpl

class LoginViewModel(private val repo: UserRepositoryImpl): ViewModel() {

    fun login(email: String, password: String) = liveData {
        val user = repo.loginUser(email, password)
        emit(user)
    }
}