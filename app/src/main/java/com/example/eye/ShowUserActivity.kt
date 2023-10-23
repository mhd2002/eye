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

        binding.tvMoney.text = separateNumber(user.money.toInt())

    }
    fun separateNumber(number: Int): String {
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