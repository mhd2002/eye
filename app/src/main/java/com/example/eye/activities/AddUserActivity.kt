package com.example.eye.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.eye.RoomDatabase.User
import com.example.eye.databinding.ActivityAddUserAtivityBinding
import com.example.eye.viewModel.MainActivityViewModel
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import android.Manifest.permission.*
import android.app.Activity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import com.example.eye.RoomDatabase.UserDao
import com.example.eye.RoomDatabase.UserDatabase
import com.example.eye.camera.CameraActivity
import okio.IOException
import java.io.File
import java.io.FileInputStream
import kotlin.math.roundToInt

class AddUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUserAtivityBinding
    private lateinit var viewModel: MainActivityViewModel
    lateinit var purchaseDate: String
    lateinit var prescriptionDate: String
    var pic: Boolean = false
    lateinit var img_bitmap: ByteArray


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserAtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]

        binding.edRightEyeSph.setOnClickListener {
            showPopupMenu(it, "edRightEyeSph")

        }

        binding.edRightEyeSyl.setOnClickListener {

            showPopupMenu(it, "edRightEyeSyl")
        }

        binding.edLeftEyeSph.setOnClickListener {
            showPopupMenu(it, "edLeftEyeSph")

        }

        binding.edLeftEyeSyl.setOnClickListener {
            showPopupMenu(it, "edLeftEyeSyl")


        }

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

                            purchaseDate = persianPickerDate?.persianYear
                                .toString() + "/" + persianPickerDate?.persianMonth
                                .toString() + "/" + persianPickerDate?.persianDay.toString()
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

                            prescriptionDate = persianPickerDate?.persianYear
                                .toString() + "/" + persianPickerDate?.persianMonth
                                .toString() + "/" + persianPickerDate?.persianDay.toString()
                            binding.edPrescriptionDate.text = prescriptionDate
                            binding.edPrescriptionDate.hint = ""
                        }

                        override fun onDismissed() {

                        }

                    })

            calender.show()
        }

        binding.btSave.setOnClickListener {

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

            } else if (binding.edPrescriptionDate.hint == "تاریخ نسخه") {
                Toast.makeText(this, "لطفا تاریخ نسخه را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edPurchaseDate.hint == "تاریخ خرید") {
                Toast.makeText(this, "لطفا تاریخ خرید را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edMoney.text.isEmpty()) {
                Toast.makeText(this, "لطفا مبلغ را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (!binding.rbCash.isChecked && !binding.rbCheck.isChecked) {
                Toast.makeText(this, "لطفا نوع پرداخت را وارد کنید.", Toast.LENGTH_SHORT).show()
///////////////////////////////////
            } else if (binding.edRightEyeAx.text.isEmpty()) {
                Toast.makeText(this, "لطفا نمره چشم راست AX را وارد کنید.", Toast.LENGTH_SHORT)
                    .show()

            } else if (binding.edRightEyeSyl.hint == "SYL") {
                Toast.makeText(this, "لطفا نمره چشم راست SYL وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edRightEyeSph.hint == "SPH") {
                Toast.makeText(this, "لطفا نمره چشم راست SPH وارد کنید.", Toast.LENGTH_SHORT).show()
////////////////////////////
            } else if (binding.edLeftEyeAx.text.isEmpty()) {
                Toast.makeText(this, "لطفا نمره چشم چپ AX را وارد کنید.", Toast.LENGTH_SHORT).show()

            } else if (binding.edLeftEyeSyl.hint == "SYL") {
                Toast.makeText(this, "لطفا نمره چشم چپ SYL را وارد کنید.", Toast.LENGTH_SHORT)
                    .show()

            } else if (binding.edLeftEyeSph.hint == "SPH") {
                Toast.makeText(this, "لطفا نمره چشم چپ SPH را وارد کنید.", Toast.LENGTH_SHORT)
                    .show()
///////////////////////////
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

            } else if (!pic) {
                Toast.makeText(this, "لطفا عکس را وارد کنید", Toast.LENGTH_SHORT).show()

            } else {

                val name = binding.edName.text.toString()
                val lastname = binding.edLastname.text.toString()
                val phone = binding.edPhoneNumber.text.toString()
                val codemeli = binding.edCodemeli.text.toString()
                val doctor = binding.edDoctor.text.toString()
                val money = binding.edMoney.text.toString()

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


                val lefteye =
                    binding.edLeftEyeSph.text.toString() + "   " + binding.edLeftEyeSyl.text.toString() +
                            "   " + binding.edLeftEyeAx.text.toString()

                val righteye =
                    binding.edRightEyeSph.text.toString() + "   " + binding.edRightEyeSyl.text.toString() +
                            "   " + binding.edRightEyeAx.text.toString()

                val user = User(
                    name = name,
                    lastName = lastname,
                    mobile = phone,
                    codeMeli = codemeli,
                    doctor = doctor,
                    prescriptionDate = prescriptionDate,
                    purchaseDate = purchaseDate,
                    money = money,
                    pay = pay,
                    RightEye = righteye,
                    LeftEye = lefteye,
                    pd = pd,
                    insurance = insurance,
                    insuranceStocks = insuranceSt,
                    organization = orgi,
                    ext = ext,
                    image_data = img_bitmap,

                )

                viewModel.insertUser(user)
                finish()

            }

        }

    }


    private fun showPopupMenu(view: View, s: String) {
        val popup = PopupMenu(this, view)

        val start = -16.0
        val end = 16.0
        val step = 0.25

        if (step <= 0) {
            throw IllegalArgumentException("Step must be a positive value")
        }

        var currentValue = start
        var menuIndex = 0

        while (currentValue <= end) {
            popup.menu.add(
                Menu.NONE,
                Menu.FIRST + menuIndex,
                Menu.NONE,
                currentValue.toString()
            )

            currentValue += step
            menuIndex++
        }

        popup.setOnMenuItemClickListener { item: MenuItem? ->
            currentValue = start


            menuIndex = 0
            while (currentValue <= end) {

                if (item!!.itemId == Menu.FIRST + menuIndex) {

                    when (s) {

                        "edRightEyeSph" -> {
                            binding.edRightEyeSph.text = currentValue.toString()
                            binding.edRightEyeSph.hint = "done"
                        }

                        "edRightEyeSyl" -> {

                            binding.edRightEyeSyl.text = currentValue.toString()
                            binding.edRightEyeSyl.hint = "done"

                        }

                        "edLeftEyeSph" -> {

                            binding.edLeftEyeSph.text = currentValue.toString()
                            binding.edLeftEyeSph.hint = "done"
                        }

                        "edLeftEyeSyl" -> {

                            binding.edLeftEyeSyl.text = currentValue.toString()
                            binding.edLeftEyeSyl.hint = "done"
                        }

                    }

                    break
                }

                currentValue += step
                menuIndex++
            }

            true
        }

        popup.show()
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
                    this@AddUserActivity,
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {


            // The child activity returned a result
            val filePath = data!!.getStringExtra("ok")

            val file = filePath?.let { File(it) }

            if (file != null) {
                if (file.exists()) {
                    try {

                        val inputStream = FileInputStream(file)
                        img_bitmap = inputStream.readBytes()
                        file.delete()
                        //     val retrievedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                        //    binding.imageView2.setImageBitmap(retrievedBitmap)
                        pic = true
                        binding.btCamera.text = "عکس گرفته شد."

                        inputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

        }


    }

    override fun onStop() {

        val file = File(this.cacheDir, "large_data.dat")

        if (file.exists()) {
            file.delete()
        }
        super.onStop()
    }

}
