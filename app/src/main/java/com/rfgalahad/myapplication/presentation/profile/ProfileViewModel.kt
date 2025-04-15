package com.rfgalahad.myapplication.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rfgalahad.myapplication.data.local.UserModel
import com.rfgalahad.myapplication.data.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repo: UserRepositoryImpl
) : ViewModel() {

    private val _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?> = _user

    fun loadUser(email: String) {
        viewModelScope.launch {
            _user.value = repo.getUser(email)
        }
    }

    fun logout() {
        _user.value = null // or clear preferences, etc.
    }
}