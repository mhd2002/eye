package com.example.eye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eye.databinding.ActivityAddUserAtivityBinding

class AddUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUserAtivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserAtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}