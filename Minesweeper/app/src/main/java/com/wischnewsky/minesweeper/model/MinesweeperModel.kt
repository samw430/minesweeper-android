package com.wischnewsky.minesweeper.model

import android.util.Log

data class SquareInfo(var mine: Boolean, var surrounding_mines: Int, var flagged: Boolean, var clicked: Boolean)

object MinesweeperModel{

    public var dimension = 5
    private var numMines = 3

    private var model = Array<Array<SquareInfo>>(dimension, {_ -> Array<SquareInfo>(dimension, {_ ->  SquareInfo(false, 0, false,false)}) })

    init{
        generateMines()
    }

    fun regenerate_game(dim: Int, mines: Int){
        dimension = dim
        numMines = mines
        model = Array<Array<SquareInfo>>(dimension, {_ -> Array<SquareInfo>(dimension, {_ ->  SquareInfo(false, 0, false,false)}) })
        generateMines()
    }

    private fun generateMines(){
        for (i in 0..numMines) {
            val randomX = (Math.random()*100 % dimension).toInt()
            val randomY = (Math.random()*100 % dimension).toInt()

            if(!model[randomX][randomY].mine) {
                model[randomX][randomY].mine = true
                updateNeighbors(randomX, randomY)
            }
        }
    }

    private fun updateNeighbors(randomX: Int, randomY: Int){
        for (x: Int in -1..1) {
            for (y: Int in -1..1) {
                val neighborX = randomX + x
                val neighborY = randomY + y

                if (neighborX < dimension && neighborX > -1 && neighborY > -1 && neighborY < dimension) {
                    model[neighborX][neighborY].surrounding_mines = model[neighborX][neighborY].surrounding_mines + 1
                }
            }
        }
    }

    private fun revealZeroNeighbors(X: Int, Y: Int){
        if(model[X][Y].surrounding_mines == 0) {
            model[X][Y].clicked = true
            for (x: Int in -1..1) {
                for (y: Int in -1..1) {
                    val neighborX = X + x
                    val neighborY = Y + y

                    if (neighborX < dimension && neighborX > -1 && neighborY > -1 && neighborY < dimension && !model[neighborX][neighborY].clicked) {
                        revealZeroNeighbors(neighborX, neighborY)
                    }
                }
            }
        }
    }

    fun setClicked(x:Int, y:Int){
        model[x][y].clicked = true
        revealZeroNeighbors(x,y)
    }

    fun setFlagged(x: Int, y: Int){
        model[x][y].flagged = true
    }

    fun isBomb(x: Int, y: Int) = model[x][y].mine

    fun wasClicked(x:Int, y:Int) = model[x][y].clicked

    fun wasFlagged(x: Int,y: Int) = model[x][y].flagged

    fun bombNeighbors(x:Int, y: Int) = model[x][y].surrounding_mines

    fun resetGame(){
        for(i in 0..dimension-1){
            for(j in 0..dimension-1){
                model[i][j] = SquareInfo(false, 0, false,false)
            }
        }
        generateMines()
    }



}