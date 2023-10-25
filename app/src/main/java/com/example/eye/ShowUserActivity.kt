package com.example.eye

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eye.Parcelable.UserID
import com.example.eye.databinding.ActivityAddUserAtivityBinding
import com.example.eye.databinding.ActivityShowUserBinding
import java.text.DecimalFormat

class ShowUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowUserBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user : UserID = intent.getParcelableExtra("ok")!!

        var pay : String = ""
        pay = if (user.pay == "0"){
            "نقدی"
        }else{
            "چکی"
        }

        binding.tvNameAndLastname.text = "نام و نام خانوادگی : " + user.name + " " + user.lastName
        binding.tvCodemeli.text = "کد ملی : " + user.codeMeli
        binding.tvPhone.text = "شماره تماس : " + user.mobile
        binding.tvDoctor.text = "دکتر : " + user.doctor
        binding.tvDate.text = "تاریخ نسخه : " + user.prescriptionDate + "  " + "تاریخ خرید : " + user.purchaseDate
        binding.tvMoney.text ="مبلغ : " + separateNumber(user.money.toInt()) + " ( " + pay  + " ) "
        binding.tvEye.text ="نمره چشم راست : " + user.RightEye + " " + " نمره چشم چپ : " + user.LeftEye
        binding.tvInsurance.text ="بیمه : " + user.insurance
        binding.tvPdAndInsuranceSt.text ="سهم بیمه : " + user.insuranceStocks + "           " + "pd : " + user.pd
        binding.tvOri.text = "سازمان : " + user.organization
        binding.tvExt.text = "توضیحات : " + user.ext

    }
    private fun separateNumber(number: Int): String {
        val numberString = number.toString()
        val length = numberString.length
        val result = StringBuilder()

        for (i in 0 until length) {
            result.append(numberString[i])
            if ((i + 1) % 3 == 0 && i != length - 1) {
                result.append(",")
            }
        }

        return result.toString()
    }
    override fun onDestroy() {
        finish()
        super.onDestroy()
    }
}