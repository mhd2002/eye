package com.example.eye.RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseName.databasename)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val lastName: String,
    val mobile: String,
    val codeMeli: String,
    val doctor: String,
    val prescriptionDate: String,
    val purchaseDate: String,
    val money: String,
    val pay: String,
    val RightEye: String,
    val LeftEye: String,
    val pd: String,
    val insurance: String,
    val insuranceStocks: String,
    val organization: String,
    val ext: String
)
