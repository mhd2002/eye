package com.example.eye

import android.annotation.SuppressLint
import android.content.Intent
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
        binding.tvRightEye.text = "نمره چشم راست : " + user.RightEye
        binding.tvLeftEye.text = "نمره چشم چپ : " + user.LeftEye
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

    override fun onDestroy() {
        finish()
        super.onDestroy()
    }
}