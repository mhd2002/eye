package com.example.eye

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.compose.animation.core.animateDpAsState
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.eye.Parcelable.UserID
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
    private val filteredList = ArrayList<PatientData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        adapter.setOnClickListener(object : PatientAdapter.OnClickListener {
            override fun onClick(position: Int) {

                if (binding.searchView.query.toString() == "") {

                    val intent = Intent(this@LaunchActivity, ShowUserActivity::class.java)

                    val user = UserID(
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

                    intent.putExtra("ok" , user)
                    startActivity(intent)

                } else {

                    val intent = Intent(this@LaunchActivity, ShowUserActivity::class.java)

                    val user = UserID(
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

                    intent.putExtra("ok" , user)
                    startActivity(intent)

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
                            0,
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
            override fun onLongClick(v: View?): Boolean {

                viewModel.deleteAllUser()
                return true
            }
        })

    }

    private fun initRecyclerView(){
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

    override fun onStart() {

        viewModel.getAllUsers()
        super.onStart()
    }

}