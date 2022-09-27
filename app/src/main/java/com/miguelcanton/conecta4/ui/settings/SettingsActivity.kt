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
import com.miguelcanton.conecta4.databinding.ActivitySettingsBinding
import com.miguelcanton.conecta4.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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

                    //Setup switch state and next animation
                    if (state.darkMode) {
                        binding.darkModeAnimation.setMinAndMaxFrame(290,440)
                        binding.darkModeAnimation.frame = 290
                    } else {
                        binding.darkModeAnimation.setMinAndMaxFrame(40,190)
                        binding.darkModeAnimation.frame = 40
                    }

                    if (state.homeNavigation) {
                        startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
                        viewModel.onNavigateToHomeScreenCompleted()
                        finish()
                    }

                    if (state.darkModeAnimation) {

                        binding.darkModeAnimation.addAnimatorListener(object :
                            Animator.AnimatorListener {

                            override fun onAnimationStart(p0: Animator) {
                                //Ignore
                            }

                            override fun onAnimationEnd(p0: Animator) {
                                //This state.darkMode is before changing its value in viewModel
                                if (!state.darkMode) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                } else {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                }
                                viewModel.onAnimationFinished()
                            }

                            override fun onAnimationCancel(p0: Animator) {
                                //Ignore
                            }

                            override fun onAnimationRepeat(p0: Animator) {
                                //Ignore
                            }

                        })

                        binding.darkModeAnimation.playAnimation()

                    }
                }
            }
        }
    }
}