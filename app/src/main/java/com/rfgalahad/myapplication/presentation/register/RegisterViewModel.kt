package com.rfgalahad.myapplication.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rfgalahad.myapplication.data.local.UserModel
import com.rfgalahad.myapplication.data.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repo: UserRepositoryImpl
) : ViewModel() {

    private val _isRegistered = MutableLiveData<Boolean>()
    val isRegistered: LiveData<Boolean> get() = _isRegistered

    fun register(user: UserModel) {
        viewModelScope.launch {
            repo.registerUser(user)
            _isRegistered.value = true
        }
    }
}