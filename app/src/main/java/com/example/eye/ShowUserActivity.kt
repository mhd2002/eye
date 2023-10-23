package com.example.eye

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eye.Parcelable.UserID
import com.example.eye.databinding.ActivityAddUserAtivityBinding
import com.example.eye.databinding.ActivityShowUserBinding

class ShowUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowUserBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user : UserID = intent.getParcelableExtra("ok")!!
       // val i1 = intent.getIntExtra("o")

        binding.textView.text = user.name + user.codeMeli

    }

    override fun onDestroy() {
        finish()
        super.onDestroy()
    }
}