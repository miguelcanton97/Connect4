package com.miguelcanton.conecta4.ui.settings

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.miguelcanton.conecta4.R
import com.miguelcanton.conecta4.databinding.ActivitySettingsBinding
import com.miguelcanton.conecta4.ui.main.MainActivity
import kotlinx.coroutines.launch


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            viewModel.onNavigateToHomeScreen()
        }

        binding.darkModeAnimation.setOnClickListener {
            viewModel.onToggleDarkMode()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.homeNavigation) {
                        startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
                        viewModel.onNavigateToHomeScreenCompleted()
                        finish()
                    }

                    if (state.darkModeAnimation) {

                        binding.darkModeAnimation.setAnimation(R.raw.dark_mode_animation)
                        if (state.darkMode) {
                            //day-to-night animation 0f-0.5f
                            binding.darkModeAnimation.setMinAndMaxProgress(0.1f, 0.4f)
                        } else {
                            //night-to-day animation 0.5f-1f
                            binding.darkModeAnimation.setMinAndMaxProgress(0.6f, 0.9f)
                        }
                        binding.darkModeAnimation.playAnimation()

                        binding.darkModeAnimation.addAnimatorListener(object :
                            Animator.AnimatorListener {
                            override fun onAnimationStart(p0: Animator) {
                                //Ignore
                            }

                            override fun onAnimationEnd(p0: Animator) {
                                if (state.darkMode) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                } else {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                }
                            }

                            override fun onAnimationCancel(p0: Animator) {
                                //Ignore
                            }

                            override fun onAnimationRepeat(p0: Animator) {
                                //Ignore
                            }

                        })
                        viewModel.onAnimationFinished()
                    }
                }
            }
        }
    }
}