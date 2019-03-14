package com.wischnewsky.minesweeper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wischnewsky.minesweeper.model.MinesweeperModel
import com.wischnewsky.minesweeper.view.MinesweeperView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        public val DIMENSION = "DIMENSION"
        public val MINES = "MINES"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEasy.setOnClickListener{
            launchGame(5,3)
        }

        btnMedium.setOnClickListener{
            launchGame(7,5)
        }

        btnHard.setOnClickListener{
            launchGame(10,10)
        }
    }

    fun launchGame(dimension: Int, numMines: Int){
        var intentDetails = Intent()
        intentDetails.setClass(this@MainActivity, GamePlayActivity::class.java)

        intentDetails.putExtra(DIMENSION, dimension)
        intentDetails.putExtra( MINES, numMines)
        startActivity(intentDetails)
    }

}
