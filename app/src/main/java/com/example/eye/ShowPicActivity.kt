package com.example.eye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.example.eye.databinding.ActivityShowPicBinding

class ShowPicActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowPicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri = intent.getStringExtra("urii")!!.toUri()
        binding.imageView.setImageURI(uri)

        binding.exit.setOnClickListener {
            finish()
        }
    }
}