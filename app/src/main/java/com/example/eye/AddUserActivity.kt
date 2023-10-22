package com.example.eye

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.eye.RoomDatabase.User
import com.example.eye.databinding.ActivityAddUserAtivityBinding
import com.example.eye.viewModel.MainActivityViewModel
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener

class AddUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUserAtivityBinding
    lateinit var viewModel: MainActivityViewModel
    lateinit var purchaseDate : String
    lateinit var prescriptionDate : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserAtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]

        binding.edPurchaseDate.setOnClickListener {
            // Toast.makeText(this, "tdjhdtjh", Toast.LENGTH_SHORT).show()
            val calender =
                PersianDatePickerDialog(this).setPositiveButtonString("تایید").setNegativeButton("")
                    .setTodayButtonVisible(true).setMinYear(1300).setInitDate(
                    PersianDatePickerDialog.THIS_YEAR,
                    PersianDatePickerDialog.THIS_MONTH,
                    PersianDatePickerDialog.THIS_DAY
                ).setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR).setShowInBottomSheet(true).setListener(object  : PersianPickerListener{
                        @SuppressLint("SetTextI18n")
                        override fun onDateSelected(persianPickerDate: PersianPickerDate?) {

                            purchaseDate = persianPickerDate?.getPersianYear().toString() + "/" + persianPickerDate?.getPersianMonth().toString() + "/" + persianPickerDate?.getPersianDay().toString()
                            binding.edPurchaseDate.text = purchaseDate
                            binding.edPurchaseDate.hint=""
                        }

                        override fun onDismissed() {

                        }

                    })

            calender.show()
        }

        binding.edPrescriptionDate.setOnClickListener {
            // Toast.makeText(this, "tdjhdtjh", Toast.LENGTH_SHORT).show()
            val calender =
                PersianDatePickerDialog(this).setPositiveButtonString("تایید").setNegativeButton("")
                    .setTodayButtonVisible(true).setMinYear(1300).setInitDate(
                        PersianDatePickerDialog.THIS_YEAR,
                        PersianDatePickerDialog.THIS_MONTH,
                        PersianDatePickerDialog.THIS_DAY
                    ).setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR).setShowInBottomSheet(true).setListener(object  : PersianPickerListener{
                        @SuppressLint("SetTextI18n")
                        override fun onDateSelected(persianPickerDate: PersianPickerDate?) {

                            prescriptionDate = persianPickerDate?.getPersianYear().toString() + "/" + persianPickerDate?.getPersianMonth().toString() + "/" + persianPickerDate?.getPersianDay().toString()
                            binding.edPrescriptionDate.text = prescriptionDate
                            binding.edPrescriptionDate.hint=""
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

            } else if (binding.edPurchaseDate.hint ==  "تاریخ خرید") {
                Toast.makeText(this, "لطفا تاریخ خرید را وارد کنید.", Toast.LENGTH_SHORT).show()

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

                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
                /*
                val user = User(
                    0, "اکبر", "اسلامی", "09137707200", "30254512365", "علی محمدی",
                    "1402/02/05", "1402/03/06", "1200000", "1", "20", "40",
                    "70", "تعمین اجتماعی", "50٪", "لتسلس", "ثبثبثبثب"
                )
                // viewModel.deleteUser(user)
                viewModel.insertUser(user)

                 */
            }


        }

    }

}
