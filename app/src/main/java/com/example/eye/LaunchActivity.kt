package com.example.eye

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.compose.animation.core.animateDpAsState
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.eye.RoomDatabase.User
import com.example.eye.databinding.ActivityLaunchBinding
import com.example.eye.recyclerView.PatientAdapter
import com.example.eye.recyclerView.PatientData
import com.example.eye.viewModel.MainActivityViewModel

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {

    lateinit var binding: ActivityLaunchBinding
    private var mList = ArrayList<PatientData>()
    private lateinit var adapter: PatientAdapter
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerXml.setHasFixedSize(true)
        binding.recyclerXml.layoutManager = LinearLayoutManager(this)

        adapter = PatientAdapter(mList, this)
        binding.recyclerXml.adapter = adapter
        adapter.setOnClickListener(object : PatientAdapter.OnClickListener {
            override fun onClick(position: Int) {

                Toast.makeText(this@LaunchActivity, position.toString(), Toast.LENGTH_SHORT).show()

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
                            " نام و نام خانوادگی : ${i.name} ${i.lastName}",
                            "کد ملی : " + i.codeMeli
                        )
                    )
                    adapter.notifyDataSetChanged()


                }

            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }

        })

        binding.btAdd.setOnClickListener {

            startActivity(Intent(this , AddUserActivity::class.java))
/*
            val user = User(
                0, "اکبر", "اسلامی", "09137707200", "30254512365", "علی محمدی",
                "1402/02/05", "1402/03/06", "1200000", "1", "20", "40",
                "70", "تعمین اجتماعی", "50٪", "لتسلس", "ثبثبثبثب"
            )
            // viewModel.deleteUser(user)
            viewModel.insertUser(user)

         //   viewModel.deleteAllUser()

 */
        }
    }

    private fun filterList(query: String?) {
        if (query != null) {

            val filteredList = ArrayList<PatientData>()

            for (i in mList) {
                if (i.nameAndLastname.contains(query) || i.codeMeli.contains(query)) {
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

    override fun onStart() {
        viewModel.getAllUsers()
        super.onStart()
    }
}