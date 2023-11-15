package com.example.eye.activities

import android.Manifest
import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eye.Parcelable.UserID
import com.example.eye.R
import com.example.eye.RoomDatabase.User
import com.example.eye.RoomDatabase.UserDatabase
import com.example.eye.databinding.ActivityLaunchBinding
import com.example.eye.recyclerView.PatientAdapter
import com.example.eye.recyclerView.PatientData
import com.example.eye.viewModel.MainActivityViewModel
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader


@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {

    lateinit var binding: ActivityLaunchBinding
    private var mList = ArrayList<PatientData>()
    private lateinit var adapter: PatientAdapter
    private lateinit var viewModel: MainActivityViewModel
    private val filteredList = ArrayList<PatientData>()
    private lateinit var inputStream: FileInputStream

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPassWord()
        initRecyclerView()

        adapter.setOnClickListener(object : PatientAdapter.OnClickListener {
            override fun onClick(position: Int, item: Int) {

                if (item == 1) {

                    if (binding.searchView.query.toString() == "") {

                        val intent = Intent(this@LaunchActivity, ShowUserActivity::class.java)

                        val file = File(this@LaunchActivity.cacheDir, "large_data.dat")

                        try {
                            val outputStream = FileOutputStream(file)
                            outputStream.write(mList[position].image_data)
                            outputStream.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        val userMList = UserID(
                            mList[position].id,
                            mList[position].name,
                            mList[position].lastName,
                            mList[position].mobile,
                            mList[position].codeMeli,
                            mList[position].doctor,
                            mList[position].prescriptionDate,
                            mList[position].purchaseDate,
                            mList[position].money,
                            mList[position].pay,
                            mList[position].RightEye,
                            mList[position].LeftEye,
                            mList[position].pd,
                            mList[position].insurance,
                            mList[position].insuranceStocks,
                            mList[position].organization,
                            mList[position].ext,
                            file.absolutePath.toString(),
                            mList[position].PatientHistory
                        )

                        intent.putExtra("ok", userMList)
                        startActivity(intent)

                    } else {

                        val intent = Intent(this@LaunchActivity, ShowUserActivity::class.java)

                        val file = File(this@LaunchActivity.cacheDir, "large_data.dat")
                        try {
                            val outputStream = FileOutputStream(file)
                            outputStream.write(mList[position].image_data)
                            outputStream.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        val userFilteredList = UserID(
                            filteredList[position].id,
                            filteredList[position].name,
                            filteredList[position].lastName,
                            filteredList[position].mobile,
                            filteredList[position].codeMeli,
                            filteredList[position].doctor,
                            filteredList[position].prescriptionDate,
                            filteredList[position].purchaseDate,
                            filteredList[position].money,
                            filteredList[position].pay,
                            filteredList[position].RightEye,
                            filteredList[position].LeftEye,
                            filteredList[position].pd,
                            filteredList[position].insurance,
                            filteredList[position].insuranceStocks,
                            filteredList[position].organization,
                            filteredList[position].ext,
                            file.absolutePath.toString(),
                            filteredList[position].PatientHistory
                        )

                        intent.putExtra("ok", userFilteredList)
                        startActivity(intent)

                    }

                } else if (item == 0) {

                    if (binding.searchView.query.toString() == "") {

                        val intent = Intent(this@LaunchActivity, EditUserActivity::class.java)

                        val file = File(this@LaunchActivity.cacheDir, "large_data.dat")
                        try {
                            val outputStream = FileOutputStream(file)
                            outputStream.write(mList[position].image_data)
                            outputStream.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        val userMList = UserID(
                            mList[position].id,
                            mList[position].name,
                            mList[position].lastName,
                            mList[position].mobile,
                            mList[position].codeMeli,
                            mList[position].doctor,
                            mList[position].prescriptionDate,
                            mList[position].purchaseDate,
                            mList[position].money,
                            mList[position].pay,
                            mList[position].RightEye,
                            mList[position].LeftEye,
                            mList[position].pd,
                            mList[position].insurance,
                            mList[position].insuranceStocks,
                            mList[position].organization,
                            mList[position].ext,
                            file.absolutePath.toString(),
                            mList[position].PatientHistory
                        )

                        intent.putExtra("ok", userMList)
                        startActivity(intent)

                    } else {

                        val intent = Intent(this@LaunchActivity, EditUserActivity::class.java)
                        val file = File(this@LaunchActivity.cacheDir, "large_data.dat")
                        try {
                            val outputStream = FileOutputStream(file)
                            outputStream.write(mList[position].image_data)
                            outputStream.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        val userFilteredList = UserID(
                            filteredList[position].id,
                            filteredList[position].name,
                            filteredList[position].lastName,
                            filteredList[position].mobile,
                            filteredList[position].codeMeli,
                            filteredList[position].doctor,
                            filteredList[position].prescriptionDate,
                            filteredList[position].purchaseDate,
                            filteredList[position].money,
                            filteredList[position].pay,
                            filteredList[position].RightEye,
                            filteredList[position].LeftEye,
                            filteredList[position].pd,
                            filteredList[position].insurance,
                            filteredList[position].insuranceStocks,
                            filteredList[position].organization,
                            filteredList[position].ext,
                            file.absolutePath.toString(),
                            filteredList[position].PatientHistory
                        )

                        intent.putExtra("ok", userFilteredList)
                        startActivity(intent)

                    }


                } else {

//                    if (binding.searchView.query.toString() == "") {
//
//                        viewModel.deleteById(mList[position].id)
//
//                    } else {
//
//                        viewModel.deleteById(filteredList[position].id)
//
//                    }

                }


            }

        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText.toString())

                return true

            }

        })

        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        viewModel.getAllUsers()

        viewModel.getAllUsersService().observe(this) {

            try {
                mList.clear()
                adapter.notifyDataSetChanged()
                for (i in it) {

                    mList.add(

                        PatientData(
                            i.id,
                            i.name,
                            i.lastName,
                            i.mobile,
                            i.codeMeli,
                            i.doctor,
                            i.prescriptionDate,
                            i.purchaseDate,
                            i.money,
                            i.pay,
                            i.RightEye,
                            i.LeftEye,
                            i.pd,
                            i.insurance,
                            i.insuranceStocks,
                            i.organization,
                            i.ext,
                            i.image_data,
                            i.PatientHistory
                        )

                    )

                    adapter.notifyDataSetChanged()

                }

            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }

        }

        binding.btAdd.setOnClickListener {

            startActivity(Intent(this, AddUserActivity::class.java))

        }

        binding.btAdd.setOnLongClickListener { v ->
            if (v != null) {
                showPopup(v)
            }
            true
        }

    }

    private fun checkPassWord() {

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val booleanValue = sharedPreferences.getBoolean("keyBoolean", false)

        if (!booleanValue) {

            val inputEditText = EditText(this)
            val builder = AlertDialog.Builder(this)

            builder.setTitle("رمز را وارد کنید !")
            builder.setView(inputEditText)

            builder.setPositiveButton("OK") { _, _ ->
                val userInput = inputEditText.text.toString()

                if (userInput == "ex@24u5m.") {

                    val editor = sharedPreferences.edit()
                    editor.putBoolean("keyBoolean", true)
                    editor.apply()

                } else {
                    Toast.makeText(this, "رمز اشتباه است.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            builder.setCancelable(false)
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }

    }

    private fun initRecyclerView() {
        binding.recyclerXml.setHasFixedSize(true)
        binding.recyclerXml.layoutManager = LinearLayoutManager(this)
        adapter = PatientAdapter(mList, this)
        binding.recyclerXml.adapter = adapter
    }

    private fun filterList(query: String?) {
        if (query != null) {

            filteredList.clear()
            for (i in mList) {
                if (i.name.contains(query) || i.codeMeli.contains(query)) {
                    filteredList.add(i)

                }

            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "یافت نشد.", Toast.LENGTH_SHORT).show()
            } else {

                adapter.setFilteredList(filteredList)

            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.menu_deleteDatabase -> {
                    delete().execute()

                }

                R.id.menu_export -> {

                    enterFileNameToImportOrExport(false)

                }

                R.id.menu_import -> {

                    enterFileNameToImportOrExport(true)

                }


            }

            true
        }

        popup.show()
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
                Log.d(TAG, "onActivityResult: Manage External Storage Permissions Granted")
            } else {
                Toast.makeText(
                    this@LaunchActivity,
                    "Storage Permissions Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun importData(fileName: String) {

        if (!checkStoragePermissions()) {

            requestForStoragePermissions()
        }

        try {
            val downloadFolderPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            val filePath = File(downloadFolderPath, "$fileName.json")
            inputStream = FileInputStream(filePath)

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

        try {
            val inputStreamReader = InputStreamReader(inputStream, Charsets.UTF_8)
            val charArray = CharArray(1024)
            val stringBuilder = StringBuilder()
            var bytesRead: Int

            while (inputStreamReader.read(charArray).also { bytesRead = it } != -1) {
                stringBuilder.appendRange(charArray, 0, bytesRead)
            }

            inputStreamReader.close()
            inputStream.close()

            val jsonString = stringBuilder.toString()
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val listType = Types.newParameterizedType(List::class.java, User::class.java)
            val adapter = moshi.adapter<List<User>>(listType)
            val dataFromJson = adapter.fromJson(jsonString)

            if (!dataFromJson.isNullOrEmpty()) {

                if (mList.isEmpty()) {

                    for (i in dataFromJson) {

                        viewModel.insertUser(i)
                    }

                } else {

                    addToDatabase(dataFromJson)
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
            //Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun addToDatabase(importedData: List<User>?) {

        for (i in mList) {

            if (importedData != null) {
                for (n in importedData) {

                    if (i.codeMeli != n.codeMeli) {

                        viewModel.insertUser(n)
                    } else if (i.purchaseDate != n.purchaseDate) {
                        viewModel.insertUser(n)
                    }

                }
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun exportData(userInput: String) {

        val userDatabase = UserDatabase.getInstance(application).UserDao()
        val dataToExport = userDatabase.getAllUserData()

        val json = Gson().toJson(dataToExport)
        val directory = Environment.DIRECTORY_DOWNLOADS

        val imageFile = File(
            Environment.getExternalStoragePublicDirectory(directory),
            "$userInput.json"
        )

        try {

            val outputStream = FileOutputStream(imageFile)
            val jsonDataBytes = json.toByteArray(Charsets.UTF_8)
            val bufferedOutputStream = BufferedOutputStream(outputStream)

            bufferedOutputStream.write(jsonDataBytes)
            bufferedOutputStream.flush()
            bufferedOutputStream.close()

            MediaScannerConnection.scanFile(this, arrayOf(imageFile.path), null, null)
        } catch (e: IOException) {

            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

        }


    }

    override fun onStart() {

        viewModel.getAllUsers()
        super.onStart()
    }

    @SuppressLint("StaticFieldLeak")
    inner class export(val userInput: String) : AsyncTask<Void, Void, Int>() {
        private lateinit var progressDialog: ProgressDialog

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            progressDialog = ProgressDialog(this@LaunchActivity)
            progressDialog.setMessage("خارج کردن اطلاعات ...")
            progressDialog.setCancelable(false)
            progressDialog.show()


            super.onPreExecute()
        }

        @Deprecated("Deprecated in Java")
        @RequiresApi(Build.VERSION_CODES.N)
        override fun doInBackground(vararg p0: Void?): Int? {

            exportData(userInput)

            return 0
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)

            progressDialog.dismiss()
            Toast.makeText(
                this@LaunchActivity,
                "file saved in /Download/$userInput.json",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    inner class import(val fileName: String) : AsyncTask<Void, Void, Int>() {
        lateinit var progressDialog: ProgressDialog
        override fun onPreExecute() {
            progressDialog = ProgressDialog(this@LaunchActivity)
            progressDialog.setMessage("وارد کردن اطلاعات ...")
            progressDialog.setCancelable(false)
            progressDialog.show()


            super.onPreExecute()
        }

        @RequiresApi(Build.VERSION_CODES.N)
        override fun doInBackground(vararg p0: Void?): Int {
            importData(fileName)

            return 0
        }

        @SuppressLint("SetTextI18n")
        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)


            progressDialog.dismiss()

        }
    }

    inner class delete : AsyncTask<Void, Void, Int>() {
        lateinit var progressDialog: ProgressDialog
        override fun onPreExecute() {
            progressDialog = ProgressDialog(this@LaunchActivity)
            progressDialog.setMessage("حذف کردن اطلاعات ...")
            progressDialog.setCancelable(false)
            progressDialog.show()


            super.onPreExecute()
        }

        @RequiresApi(Build.VERSION_CODES.N)
        override fun doInBackground(vararg p0: Void?): Int {

            viewModel.deleteAllUser()

            return 0
        }

        @SuppressLint("SetTextI18n")
        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)

            progressDialog.dismiss()

        }
    }

    override fun onStop() {

        val file = File(this.cacheDir, "large_data.dat")

        if (file.exists()) {
            file.delete()
        }
        super.onStop()
    }

    private fun enterFileNameToImportOrExport(isImport: Boolean) {


        val inputEditText = EditText(this)
        val builder = AlertDialog.Builder(this)

        if (isImport) {
            builder.setTitle("نام فایل برای وارد کردن اطلاعات وارد کنید.")
        } else {
            builder.setTitle("نام فایل برای خروج کردن اطلاعات وارد کنید.")
        }

        builder.setView(inputEditText)

        builder.setPositiveButton("OK") { _, _ ->

            val userInput = inputEditText.text.toString()

            if (isImport) {
                import(userInput).execute()
            } else {
                export(userInput).execute()
            }


        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()


    }

}


