package com.example.eye.RoomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM ${DatabaseName.databasename}")
     fun deleteAll()

    @Query("SELECT * FROM ${DatabaseName.databasename} order by id desc ")
    fun getAllUser():List<User>

    @Query("SELECT * FROM ${DatabaseName.databasename}")
    fun getAllUserData():List<User>
}