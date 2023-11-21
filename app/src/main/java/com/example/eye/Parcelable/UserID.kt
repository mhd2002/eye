package com.example.eye.Parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserID(
    val id:Long,
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
    val ext: String,
    val image_data: String,

): Parcelable
