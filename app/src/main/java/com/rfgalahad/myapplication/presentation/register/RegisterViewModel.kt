package com.rfgalahad.myapplication.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rfgalahad.myapplication.data.local.UserModel
import com.rfgalahad.myapplication.data.repository.UserRepositoryImpl

class RegisterViewModel(
    private val repo: UserRepositoryImpl
) : ViewModel() {

    fun register(user: UserModel) = liveData {
        val result = repo.registerUser(user)
        emit(result)
    }
}