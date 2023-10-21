package com.example.eye.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun UserDao(): UserDao

    companion object {

        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {

            if (instance == null)
                instance = Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,
                    DatabaseName.databasename
                ).allowMainThreadQueries().build()

            return instance as UserDatabase

        }
    }

}