package com.rfgalahad.myapplication.data.repository

import android.content.Context
import com.rfgalahad.myapplication.data.local.LocalDB

object UserRepositoryProvider {

    fun provide(context: Context): UserRepositoryImpl {
        val dao = LocalDB.getDB(context).userDao()
        return UserRepositoryImpl(dao)
    }
}