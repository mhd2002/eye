package com.example.eye.activities

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eye.Parcelable.UserID
import com.example.eye.databinding.ActivityShowUserBinding
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ShowUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowUserBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user: UserID = intent.getParcelableExtra("ok")!!

        val pay = if (user.pay == "0") {
            "نقدی"
        } else {
            "چکی"
        }

        binding.tvNameAndLastname.text = "نام و نام خانوادگی : " + user.name + " " + user.lastName
        binding.tvCodemeli.text = "کد ملی : " + user.codeMeli
        binding.tvPhone.text = "شماره تماس : " + user.mobile
        binding.tvDoctor.text = "دکتر : " + user.doctor
        binding.tvDate.text =
            "تاریخ نسخه : " + user.prescriptionDate + "  " + "تاریخ خرید : " + user.purchaseDate
        binding.tvMoney.text = "مبلغ : " + separateNumber(user.money.toLong()) + " ( " + pay + " ) "

        binding.tvLeftEye.text = "OD : " + user.RightEye//rearrangeNumbers(user.RightEye)
        binding.tvRightEye.text = "OS : " + user.LeftEye//rearrangeNumbers(user.LeftEye)
        binding.tvInsurance.text = "بیمه : " + user.insurance
        binding.tvPdAndInsuranceSt.text =
            "سهم بیمه : " + separateNumber(user.insuranceStocks.toLong()) + "    " + "pd : " + user.pd
        binding.tvOri.text = "سازمان : " + user.organization
        binding.tvExt.text = "توضیحات : " + user.ext

        val file = File(user.image_data)

        if (file.exists()) {
            try {

                val inputStream = FileInputStream(file)
                val img_bitmap = inputStream.readBytes()

                val retrievedBitmap =
                    BitmapFactory.decodeByteArray(img_bitmap, 0, img_bitmap.size)
                binding.im.setImageBitmap(retrievedBitmap)
                file.delete()
                

                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


    }

    private fun separateNumber(number: Long): String {
        return String.format("%,d", number)
    }
    fun rearrangeNumbers(input: String): String {
        // Step 1: Split the string into individual numbers
        val numbers = input.split(" ")

        // Step 2: Rearrange the numbers in the desired order
        val rearrangedNumbers = numbers.reversed()

        // Step 3: Join the numbers into a string and update the TextView
        return rearrangedNumbers.joinToString(" ")
    }
    override fun onDestroy() {
        finish()
        super.onDestroy()
    }
}