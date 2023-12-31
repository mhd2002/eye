package com.example.eye.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.eye.Parcelable.UserID
import com.example.eye.R
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
                val bit = retrievedBitmap
                binding.im.setOnClickListener {
                    showImageDialog(bit)
                }

                file.delete()


                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    private fun showImageDialog(bitmap: Bitmap) {

        val imageView = ImageView(this)
        imageView.setImageBitmap(bitmap)

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        imageView.layoutParams = layoutParams

        imageView.scaleType = ImageView.ScaleType.FIT_XY
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setView(imageView)
            .setPositiveButton("OK") { _, _ ->

            }.show()
    }

    private fun separateNumber(number: Long): String {
        return String.format("%,d", number)
    }

    override fun onDestroy() {
        finish()
        super.onDestroy()
    }
}