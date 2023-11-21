package com.example.eye.RoomDatabase

import android.net.Uri
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
    val ext: String,
    val image_data : ByteArray ,

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false
        if (lastName != other.lastName) return false
        if (mobile != other.mobile) return false
        if (codeMeli != other.codeMeli) return false
        if (doctor != other.doctor) return false
        if (prescriptionDate != other.prescriptionDate) return false
        if (purchaseDate != other.purchaseDate) return false
        if (money != other.money) return false
        if (pay != other.pay) return false
        if (RightEye != other.RightEye) return false
        if (LeftEye != other.LeftEye) return false
        if (pd != other.pd) return false
        if (insurance != other.insurance) return false
        if (insuranceStocks != other.insuranceStocks) return false
        if (organization != other.organization) return false
        if (ext != other.ext) return false
        if (!image_data.contentEquals(other.image_data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + mobile.hashCode()
        result = 31 * result + codeMeli.hashCode()
        result = 31 * result + doctor.hashCode()
        result = 31 * result + prescriptionDate.hashCode()
        result = 31 * result + purchaseDate.hashCode()
        result = 31 * result + money.hashCode()
        result = 31 * result + pay.hashCode()
        result = 31 * result + RightEye.hashCode()
        result = 31 * result + LeftEye.hashCode()
        result = 31 * result + pd.hashCode()
        result = 31 * result + insurance.hashCode()
        result = 31 * result + insuranceStocks.hashCode()
        result = 31 * result + organization.hashCode()
        result = 31 * result + ext.hashCode()
        result = 31 * result + image_data.contentHashCode()
        return result
    }
}
