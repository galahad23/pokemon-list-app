package com.rfgalahad.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserModel::class], version = 1)
abstract class LocalDB : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object {
        @Volatile private var instance: LocalDB? = null

        fun getDB(context: Context): LocalDB {
            return instance ?: synchronized(this) {
                val localDbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDB::class.java,
                    "poke_app_db"
                ).build()
                instance = localDbInstance
                localDbInstance
            }
        }
    }
}