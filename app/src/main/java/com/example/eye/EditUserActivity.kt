package com.example.eye

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.eye.Parcelable.UserID
import com.example.eye.RoomDatabase.User
import com.example.eye.databinding.ActivityEditUserBinding
import com.example.eye.viewModel.MainActivityViewModel
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import kotlin.properties.Delegates

class EditUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditUserBinding
    lateinit var viewModel: MainActivityViewModel
    lateinit var purchaseDate: String
    lateinit var prescriptionDate: String
    var id by Delegates.notNull<Long>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValue()

        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]

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
                var pay: String = ""

                if (binding.rbCash.isChecked) {
                    pay = "0"
                } else if (binding.rbCheck.isChecked) {
                    pay = "1"
                }

                val user = User(
                   id , name, lastname, phone, codemeli, doctor,
                    prescriptionDate, purchaseDate, money, pay, righteye, lefteye,
                    pd, insurance, insuranceSt, orgi, ext
                )

                viewModel.updateUser(user)
                finish()

            }

        }

    }

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
    }
}