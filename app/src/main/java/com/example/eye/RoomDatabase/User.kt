package com.example.eye.RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseName.databasename)

data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name =  "id")val id:Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "mobile") val mobile: String,
    @ColumnInfo(name = "codeMeli") val codeMeli: String,
    @ColumnInfo(name = "doctor") val doctor: String,
    @ColumnInfo(name = "prescriptionDate") val prescriptionDate: String,
    @ColumnInfo(name = "purchaseDate") val purchaseDate: String,
    @ColumnInfo(name = "money") val money: String,
    @ColumnInfo(name = "pay") val pay: String,
    @ColumnInfo(name = "RightEye") val RightEye: String,
    @ColumnInfo(name = "LeftEye") val LeftEye: String,
    @ColumnInfo(name = "pd") val pd: String,
    @ColumnInfo(name = "insurance") val insurance: String,
    @ColumnInfo(name = "insuranceStocks") val insuranceStocks: String,
    @ColumnInfo(name = "organization") val organization: String,
    @ColumnInfo(name = "ext") val ext: String

)
