package com.example.eye

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.eye.databinding.ActivityLaunchBinding
import com.example.eye.recyclerView.PatientAdapter
import com.example.eye.recyclerView.PatientData

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {

    lateinit var binding: ActivityLaunchBinding
    private var mList = ArrayList<PatientData>()
    private lateinit var adapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerXml.setHasFixedSize(true)
        binding.recyclerXml.layoutManager = LinearLayoutManager(this)

        addDataToList()
        adapter = PatientAdapter(mList , this)
        binding.recyclerXml.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText.toString())
                return true

            }

        })

        adapter.setOnClickListener(object : PatientAdapter.OnClickListener{
            override fun onClick(position: Int) {

                Toast.makeText(this@LaunchActivity, position.toString(), Toast.LENGTH_SHORT).show()

            }

        })

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

    private fun addDataToList() {

            val name = "نام و نام خانوادگی : "
            val  meli= "کد ملی : "

        mList.add(PatientData(name+"محمد مهدی رنحبر", meli+"3080377451"))
        mList.add(PatientData(name+"علی علوی", meli+"1230377102"))
        mList.add(PatientData(name+"حسین عسکری", meli+"1020384125"))
        mList.add(PatientData(name+"محمد امین رونقی", meli+"1234567891"))
        mList.add(PatientData(name+"محمد طاها تهمورسی", meli+"1203597514"))
        mList.add(PatientData(name+"بارسا بوراحمدی", meli+"4593202478"))
        mList.add(PatientData(name+"مبین عاقلی", meli+"36298754154"))
        mList.add(PatientData(name+"متین نوش زاده", meli+"7912436518"))
    }
}