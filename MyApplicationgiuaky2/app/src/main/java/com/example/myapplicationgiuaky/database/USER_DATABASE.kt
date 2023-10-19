package com.example.myapplicationgiuaky.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplicationgiuaky.Model.user


@Database(entities = [user::class], version = 1)
abstract class USER_DATABASE: RoomDatabase() {
    abstract fun userDao(): USER_DAO

    companion object {
        private var INSTANCE: USER_DATABASE?= null
        private val DB_NAME = "user_db"

        fun getDatabase(context: Context): USER_DATABASE {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    USER_DATABASE::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}

