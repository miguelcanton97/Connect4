package com.miguelcanton.conecta4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miguelcanton.conecta4.databinding.ActivityGameBinding
import com.miguelcanton.conecta4.databinding.ActivityMainBinding

class GameActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityGameBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding = ActivityGameBinding.inflate(layoutInflater)
    }
}