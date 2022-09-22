package com.miguelcanton.conecta4.ui.game

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.miguelcanton.conecta4.R
import com.miguelcanton.conecta4.databinding.ActivityGameBinding
import com.miguelcanton.conecta4.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.restartIcon.setOnClickListener { viewModel.onRestartGame() }
        binding.restartText.setOnClickListener { viewModel.onRestartGame() }

        binding.homeIcon.setOnClickListener { viewModel.onNavigateToHome() }
        binding.homeText.setOnClickListener { viewModel.onNavigateToHome() }

        binding.cleanIcon.setOnClickListener { viewModel.onCleanScore() }
        binding.cleanText.setOnClickListener { viewModel.onCleanScore() }

        for (index in 0 until binding.boardGridLayout.size){
            binding.boardGridLayout.getChildAt(index).setOnClickListener{
                //Toast.makeText(this@GameActivity, "Elemento clickado $index", Toast.LENGTH_SHORT).show()
                viewModel.chipClicked(index)
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->

                    binding.resultTextView.text = getString(R.string.score_placeholders,state.player1Wins, state.player2Wins)
                    binding.player1TextView.text = if (state.playerTurn == GameViewModel.Players.PLAYER1) getText(R.string.red_underlined) else getString(R.string.red)
                    binding.player2TextView.text = if (state.playerTurn == GameViewModel.Players.PLAYER2) getText(R.string.blue_underlined) else getString(R.string.blue)

                    if (state.navigateToHome){
                        startActivity(Intent(this@GameActivity, MainActivity::class.java))
                        viewModel.navigatedToHomeCompleted()
                        finish()
                    }

                    if(state.addNewChip && state.boardEnabled){
                        val image = binding.boardGridLayout.getChildAt(state.newChip.first) as ImageView
                        if (state.newChip.second == GameViewModel.Players.PLAYER1){
                            image.setImageResource(R.drawable.chip_red_white_dotted)
                        }
                        if (state.newChip.second == GameViewModel.Players.PLAYER2){
                            image.setImageResource(R.drawable.chip_blue_white_dotted)
                        }
                        viewModel.newChipAdded()
                    }

                    if(state.chipsWinIndex.isNotEmpty()){
                        for(index in state.chipsWinIndex){
                            val image = binding.boardGridLayout.getChildAt(index) as ImageView
                            if (state.playerTurn == GameViewModel.Players.PLAYER1){
                                image.setImageResource(R.drawable.chip_star_red_white_dotted)
                            }
                            if (state.playerTurn == GameViewModel.Players.PLAYER2){
                                image.setImageResource(R.drawable.chip_star_blue_white_dotted)
                            }
                        }
                        delay(10000)
                        cleanBoard()
                        viewModel.chipsWinShowed()
                    }

                    if (state.showError){
                        Toast.makeText(this@GameActivity, "Columna completa", Toast.LENGTH_SHORT).show()
                        viewModel.showedError()
                    }

                    if (state.restartGame){
                        cleanBoard()
                        viewModel.gameRestarted()
                    }

                    if (state.cleanBoard){
                        cleanBoard()
                        viewModel.gameRestarted()
                    }
                }
            }
        }
    }

    private fun cleanBoard() {
        for (index in 0 until binding.boardGridLayout.size){
            val image = binding.boardGridLayout.getChildAt(index) as ImageView
            image.setImageResource(R.drawable.chip_empty)
        }
    }
}