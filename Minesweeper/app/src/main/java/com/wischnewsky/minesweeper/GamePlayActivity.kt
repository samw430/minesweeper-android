package com.wischnewsky.minesweeper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wischnewsky.minesweeper.view.MinesweeperView
import kotlinx.android.synthetic.main.activity_game_play.*

class GamePlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)

        if(intent.extras.containsKey(MainActivity.DIMENSION) && intent.extras.containsKey(MainActivity.MINES)){
            val dim = intent.getIntExtra(MainActivity.DIMENSION, 5)
            val mines = intent.getIntExtra(MainActivity.MINES,3)

            minesweaperView.onStart(dim, mines)

        }

        resetBtn.setOnClickListener{
            minesweaperView.resetGame()
        }
    }

    fun flagsOn(): Boolean = flagToggle.isChecked
}
