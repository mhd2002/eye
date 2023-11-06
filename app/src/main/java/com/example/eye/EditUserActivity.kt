package com.example.eye

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProviders
import com.example.eye.Parcelable.UserID
import com.example.eye.RoomDatabase.User
import com.example.eye.databinding.ActivityEditUserBinding
import com.example.eye.viewModel.MainActivityViewModel
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import kotlin.properties.Delegates

class EditUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditUserBinding
    lateinit var viewModel: MainActivityViewModel
    lateinit var purchaseDate: String
    lateinit var prescriptionDate: String
    var id by Delegates.notNull<Long>()
    lateinit var image_data: ByteArray
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValue()

        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]

        binding.btCamera.setOnClickListener {

            if (!checkStoragePermissions()) {

                requestForStoragePermissions()
            }

            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

                val permission = arrayOf(Manifest.permission.CAMERA)
                requestPermissions(permission, 100)

            } else {

                val intent = Intent(this, CameraActivity::class.java)
                startActivityForResult(intent, 103)

            }

        }

        binding.edPurchaseDate.setOnClickListener {
            // Toast.makeText(this, "tdjhdtjh", Toast.LENGTH_SHORT).show()
            val calender =
                PersianDatePickerDialog(this).setPositiveButtonString("تایید").setNegativeButton("")
                    .setTodayButtonVisible(true).setMinYear(1300).setInitDate(
                        PersianDatePickerDialog.THIS_YEAR,
                        PersianDatePickerDialog.THIS_MONTH,
                        PersianDatePickerDialog.THIS_DAY
                    ).setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                    .setShowInBottomSheet(true).setListener(object : PersianPickerListener {
                        @SuppressLint("SetTextI18n")
                        override fun onDateSelected(persianPickerDate: PersianPickerDate?) {

                            purchaseDate = persianPickerDate?.getPersianYear()
                                .toString() + "/" + persianPickerDate?.getPersianMonth()
                                .toString() + "/" + persianPickerDate?.getPersianDay().toString()
                            binding.edPurchaseDate.text = purchaseDate
                            binding.edPurchaseDate.hint = ""
                        }

                        override fun onDismissed() {

                        }

                    })

            calender.show()
        }

        binding.edPrescriptionDate.setOnClickListener {
            val calender =
                PersianDatePickerDialog(this).setPositiveButtonString("تایید").setNegativeButton("")
                    .setTodayButtonVisible(true).setMinYear(1300).setInitDate(
                        PersianDatePickerDialog.THIS_YEAR,
                        PersianDatePickerDialog.THIS_MONTH,
                        PersianDatePickerDialog.THIS_DAY
                    ).setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                    .setShowInBottomSheet(true).setListener(object : PersianPickerListener {
                        @SuppressLint("SetTextI18n")
                        override fun onDateSelected(persianPickerDate: PersianPickerDate?) {

                            prescriptionDate = persianPickerDate?.getPersianYear()
                                .toString() + "/" + persianPickerDate?.getPersianMonth()
                                .toString() + "/" + persianPickerDate?.getPersianDay().toString()
                            binding.edPrescriptionDate.text = prescriptionDate
                            binding.edPrescriptionDate.hint = ""
                        }

                        override fun onDismissed() {

                        }

                    })

            calender.show()
        }

        binding.btUpdate.setOnClickListener {

            if (binding.edName.text.isEmpty()) {
                Toast.makeText(this, "لطفا نام را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edLastname.text.isEmpty()) {
                Toast.makeText(this, "لطفا نام خانوادگی را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edCodemeli.text.isEmpty()) {
                Toast.makeText(this, "لطفا کدملی را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edPhoneNumber.text.isEmpty()) {
                Toast.makeText(this, "لطفا شماره تماس را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edDoctor.text.isEmpty()) {
                Toast.makeText(this, "لطفا نام دکتر را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edMoney.text.isEmpty()) {
                Toast.makeText(this, "لطفا مبلغ را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (!binding.rbCash.isChecked && !binding.rbCheck.isChecked) {
                Toast.makeText(this, "لطفا نوع پرداخت را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edRightEye.text.isEmpty()) {
                Toast.makeText(this, "لطفا نمره چشم راست را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edLeftEye.text.isEmpty()) {
                Toast.makeText(this, "لطفا نمره چشم چپ را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edPd.text.isEmpty()) {
                Toast.makeText(this, "لطفا pd را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edInsurance.text.isEmpty()) {
                Toast.makeText(this, "لطفا نام بیمه را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edInsuranceStocks.text.isEmpty()) {
                Toast.makeText(this, "لطفا سهم بیمه را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edOrganization.text.isEmpty()) {
                Toast.makeText(this, "لطفا سازمان را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edExt.text.isEmpty()) {
                Toast.makeText(this, "لطفا توضیحات را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else {

                val name = binding.edName.text.toString()
                val lastname = binding.edLastname.text.toString()
                val phone = binding.edPhoneNumber.text.toString()
                val codemeli = binding.edCodemeli.text.toString()
                val doctor = binding.edDoctor.text.toString()
                val money = binding.edMoney.text.toString()
                val lefteye = binding.edLeftEye.text.toString()
                val righteye = binding.edRightEye.text.toString()
                val pd = binding.edPd.text.toString()
                val insurance = binding.edInsurance.text.toString()
                val insuranceSt = binding.edInsuranceStocks.text.toString()
                val orgi = binding.edOrganization.text.toString()
                val ext = binding.edExt.text.toString()
                var pay = ""

                if (binding.rbCash.isChecked) {
                    pay = "0"
                } else if (binding.rbCheck.isChecked) {
                    pay = "1"
                }

                val user = User(
                   id , name, lastname, phone, codemeli, doctor,
                    prescriptionDate, purchaseDate, money, pay, righteye, lefteye,
                    pd, insurance, insuranceSt, orgi, ext , image_data

                )

                viewModel.updateUser(user)
                finish()

            }

        }

    }

    @SuppressLint("SuspiciousIndentation")
    fun setValue() {
        val user: UserID = intent.getParcelableExtra("ok")!!

        binding.edName.setText(user.name)
        binding.edLastname.setText(user.lastName)
        binding.edCodemeli.setText(user.codeMeli)
        binding.edPhoneNumber.setText(user.mobile)
        binding.edDoctor.setText(user.doctor)
        binding.edPrescriptionDate.text = user.prescriptionDate
        binding.edPurchaseDate.text = user.purchaseDate
        binding.edMoney.setText(user.money)
        binding.edRightEye.setText(user.RightEye)
        binding.edLeftEye.setText(user.LeftEye)
        binding.edPd.setText(user.pd)
        binding.edInsurance.setText(user.insurance)
        binding.edInsuranceStocks.setText(user.insuranceStocks)
        binding.edOrganization.setText(user.organization)
        binding.edExt.setText(user.ext)

        purchaseDate = user.purchaseDate
        prescriptionDate = user.prescriptionDate

        id = user.id
        if (user.pay == "0") {
            binding.rbCash.toggle()

        } else {
            binding.rbCheck.toggle()
        }

        val file = File(user.image_data)

        if (file.exists()) {
            try {

                val inputStream = FileInputStream(file)
                val img_bitmap = inputStream.readBytes()

                image_data = img_bitmap

                    inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            val filePath =   data!!.getStringExtra("ok")

            val file = filePath?.let { File(it) }

            if (file != null) {
                if (file.exists()) {
                    try {

                        val inputStream = FileInputStream(file)
                        image_data = inputStream.readBytes()

                        //     val retrievedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                        //    binding.imageView2.setImageBitmap(retrievedBitmap)

                        binding.btCamera.text = "عکس گرفته شد."

                        inputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

        }


    }

    private val STORAGE_PERMISSION_CODE = 23
    private fun requestForStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                storageActivityResultLauncher.launch(intent)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                storageActivityResultLauncher.launch(intent)
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    private val storageActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Log.d(
                    ContentValues.TAG,
                    "onActivityResult: Manage External Storage Permissions Granted"
                )
            } else {
                Toast.makeText(
                    this@EditUserActivity,
                    "Storage Permissions Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun checkStoragePermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val write =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val read =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            read == PackageManager.PERMISSION_GRANTED && write == PackageManager.PERMISSION_GRANTED

        }
    }

}