package com.example.eye

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eye.Parcelable.UserID
import com.example.eye.RoomDatabase.User
import com.example.eye.RoomDatabase.UserDatabase
import com.example.eye.databinding.ActivityLaunchBinding
import com.example.eye.recyclerView.PatientAdapter
import com.example.eye.recyclerView.PatientData
import com.example.eye.viewModel.MainActivityViewModel
import com.google.gson.Gson
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import android.Manifest
import android.Manifest.permission.*
import android.content.ContentValues.TAG
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {

    lateinit var binding: ActivityLaunchBinding
    private var mList = ArrayList<PatientData>()
    private lateinit var adapter: PatientAdapter
    lateinit var viewModel: MainActivityViewModel
    private val filteredList = ArrayList<PatientData>()
    private lateinit var inputStream: FileInputStream
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPassWord()
        initRecyclerView()

        adapter.setOnClickListener(object : PatientAdapter.OnClickListener {
            override fun onClick(position: Int, item: Boolean) {

                if (item) {

                    if (binding.searchView.query.toString() == "") {

                        val intent = Intent(this@LaunchActivity, ShowUserActivity::class.java)

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
                            mList[position].ext
                        )

                        intent.putExtra("ok", userMList)
                        startActivity(intent)

                    } else {

                        val intent = Intent(this@LaunchActivity, ShowUserActivity::class.java)

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
                            filteredList[position].ext
                        )

                        intent.putExtra("ok", userFilteredList)
                        startActivity(intent)

                    }

                } else {

                    if (binding.searchView.query.toString() == "") {

                        val intent = Intent(this@LaunchActivity, EditUserActivity::class.java)

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
                            mList[position].ext
                        )

                        intent.putExtra("ok", userMList)
                        startActivity(intent)

                    } else {

                        val intent = Intent(this@LaunchActivity, EditUserActivity::class.java)

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
                            filteredList[position].ext
                        )

                        intent.putExtra("ok", userFilteredList)
                        startActivity(intent)

                    }


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

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getAllUsers()

        viewModel.getAllUsersService().observe(this, Observer {

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
                            i.ext
                        )

                    )

                    adapter.notifyDataSetChanged()

                }

            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }

        })

        binding.btAdd.setOnClickListener {

            startActivity(Intent(this, AddUserActivity::class.java))

        }

        binding.btAdd.setOnLongClickListener(object : View.OnLongClickListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onLongClick(v: View?): Boolean {

                if (v != null) {
                    showPopup(v)
                }
                return true
            }
        })

    }

    private fun checkPassWord(){

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

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.menu_deleteDatabase -> {
                    viewModel.deleteAllUser()

                }

                R.id.menu_export -> {
                    exportData()
                }

                R.id.menu_import -> {
                    importData()
                }
            }

            true
        })

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
    private fun importData() {

        if (!checkStoragePermissions()) {

            requestForStoragePermissions()
        }

        try {
            val downloadFolderPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val filePath = File(downloadFolderPath, "exported_data.json")
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

                    for (i in dataFromJson) {

                        for (n in mList) {

                            if (i.codeMeli != n.codeMeli) {
                                viewModel.insertUser(i)
                            }

                        }

                    }

                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }

    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun exportData() {

        val userDatabase = UserDatabase.getInstance(application).UserDao()
        val dataToExport = userDatabase.getAllUserData()

        val json = Gson().toJson(dataToExport)

        val directory = Environment.DIRECTORY_DOWNLOADS
        val fileName = "exported_data.json"

        val imageFile = File(Environment.getExternalStoragePublicDirectory(directory), fileName)


        try {

            val outputStream = FileOutputStream(imageFile)

            val jsonDataBytes = json.toByteArray(Charsets.UTF_8)

            val outputStreamWriter = OutputStreamWriter(outputStream, Charsets.UTF_8)
            val bufferedOutputStream = BufferedOutputStream(outputStream)

            bufferedOutputStream.write(jsonDataBytes)
            bufferedOutputStream.flush()

            bufferedOutputStream.close()

            MediaScannerConnection.scanFile(this, arrayOf(imageFile.path), null, null)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            Toast.makeText(this, "file saved in /Download", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onStart() {

        viewModel.getAllUsers()
        super.onStart()
    }

}