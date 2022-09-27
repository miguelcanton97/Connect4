package com.miguelcanton.conecta4.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.miguelcanton.conecta4.R
import com.miguelcanton.conecta4.databinding.ActivityMainBinding
import com.miguelcanton.conecta4.ui.game.GameActivity
import com.miguelcanton.conecta4.ui.settings.SettingsActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener {
            viewModel.onNavigateToGameScreen()
        }

        binding.settingsButton.setOnClickListener {
            viewModel.onNavigateToSettingsScreen()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.gameNavigation) {
                        startActivity(Intent(this@MainActivity, GameActivity::class.java))
                        viewModel.onNavigateToGameScreenCompleted()
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    if (state.settingsNavigation) {
                        startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        viewModel.onNavigateToSettingsScreenCompleted()
                    }
                }
            }
        }
    }
}