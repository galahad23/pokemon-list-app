package com.rfgalahad.myapplication.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseViewModelFactory<T: ViewModel>(
    private val creator: () -> T
) : ViewModelProvider.Factory {

    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
        return creator() as VM
    }
}