package com.example.eye

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eye.Parcelable.UserID
import com.example.eye.databinding.ActivityShowUserBinding

class ShowUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowUserBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user : UserID = intent.getParcelableExtra("ok")!!

        val pay = if (user.pay == "0"){
            "نقدی"
        }else{
            "چکی"
        }

        binding.tvNameAndLastname.text = "نام و نام خانوادگی : " + user.name + " " + user.lastName
        binding.tvCodemeli.text = "کد ملی : " + user.codeMeli
        binding.tvPhone.text = "شماره تماس : " + user.mobile
        binding.tvDoctor.text = "دکتر : " + user.doctor
        binding.tvDate.text = "تاریخ نسخه : " + user.prescriptionDate + "  " + "تاریخ خرید : " + user.purchaseDate
        binding.tvMoney.text ="مبلغ : " + separateNumber(user.money.toLong()) + " ( " + pay  + " ) "
        binding.tvRightEye.text ="نمره چشم راست : " + user.RightEye
        binding.tvLeftEye.text ="نمره چشم چپ : " + user.LeftEye
        binding.tvInsurance.text ="بیمه : " + user.insurance
        binding.tvPdAndInsuranceSt.text ="سهم بیمه : " + separateNumber(user.insuranceStocks.toLong()) + "    " + "pd : " + user.pd
        binding.tvOri.text = "سازمان : " + user.organization
        binding.tvExt.text = "توضیحات : " + user.ext

        binding.btCamera.setOnClickListener {

            val intent = Intent(this , ShowPicActivity::class.java)
            intent.putExtra("urii" , user.uri)
            startActivity(intent)

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