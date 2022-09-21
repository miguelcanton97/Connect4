package com.miguelcanton.conecta4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miguelcanton.conecta4.databinding.ActivityGameBinding
import com.miguelcanton.conecta4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.playButton.setOnClickListener{

        }
    }
}