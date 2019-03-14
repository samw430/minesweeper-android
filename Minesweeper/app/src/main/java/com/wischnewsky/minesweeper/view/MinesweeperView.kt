package com.wischnewsky.minesweeper.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.wischnewsky.minesweeper.GamePlayActivity
import com.wischnewsky.minesweeper.MainActivity
import com.wischnewsky.minesweeper.R
import com.wischnewsky.minesweeper.model.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs){

    private val paintLine = Paint()
    private val paintBackground = Paint()
    private val paintNumber = Paint()
    private val paintBomb = Paint()

    init {
        paintBackground.color = Color.GRAY
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.BLACK
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 2f

        paintNumber.color = Color.RED
        paintNumber.textSize = 50f

        paintBomb.color = Color.BLUE
        paintBomb.textSize = 50f

    }

    public fun onStart(dim: Int, mines: Int){
        MinesweeperModel.regenerate_game(dim, mines)
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintNumber.textSize = (height/MinesweeperModel.dimension).toFloat()
        paintBomb.textSize = (height/MinesweeperModel.dimension).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f,0f, width.toFloat(), height.toFloat(), paintBackground)
        drawGameBoard(canvas)
        drawViewableNumbers(canvas)
    }

    private fun drawViewableNumbers(canvas: Canvas?){
        var allFlagged = true

        for(i in 0..MinesweeperModel.dimension-1){
            for(j in 0..MinesweeperModel.dimension-1){
                if(MinesweeperModel.wasClicked(i,j) && MinesweeperModel.isBomb(i,j)) {
                    endLoss()
                }else if(MinesweeperModel.wasFlagged(i,j) && !MinesweeperModel.isBomb(i,j)){
                    endLoss()
                }else if(MinesweeperModel.wasFlagged(i,j)){
                    drawCross(i,j,canvas)
                }else if(MinesweeperModel.wasClicked(i,j)){
                    canvas?.drawText(MinesweeperModel.bombNeighbors(i,j).toString(), ((i)*(width/MinesweeperModel.dimension)).toFloat(), ((j+1)*(height/MinesweeperModel.dimension)).toFloat(), paintNumber )
                }else if(MinesweeperModel.isBomb(i,j) && !MinesweeperModel.wasFlagged(i,j)){
                    allFlagged = false
                }
            }
        }
        if(allFlagged){
            endWin()
        }
    }

    private fun endWin(){
        this.resetGame()
        Toast.makeText(context, context.getString(R.string.win_toast), Toast.LENGTH_LONG).show()
    }
    
    private fun endLoss(){
        this.resetGame()
        Toast.makeText(context, context.getString(R.string.loss_toast), Toast.LENGTH_LONG).show()
    }

    private fun drawGameBoard(canvas: Canvas?) {
        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        for(i in 0..MinesweeperModel.dimension-1){
            canvas?.drawLine(0f, (i*(height/MinesweeperModel.dimension)).toFloat(), width.toFloat(), (i*(height/MinesweeperModel.dimension)).toFloat(), paintLine)
            canvas?.drawLine((i*(width/MinesweeperModel.dimension)).toFloat(), 0f, (i*(width/MinesweeperModel.dimension)).toFloat(), height.toFloat(), paintLine)
        }
    }

    fun resetGame(){
        MinesweeperModel.resetGame()
        invalidate()
    }

    fun drawCross(i:Int, j:Int, canvas: Canvas?){
        paintLine.color = Color.GREEN
        canvas?.drawLine((i * width / MinesweeperModel.dimension).toFloat(), (j * height / MinesweeperModel.dimension).toFloat(),
            ((i + 1) * width / MinesweeperModel.dimension).toFloat(),
            ((j + 1) * height / MinesweeperModel.dimension).toFloat(), paintLine)

        canvas?.drawLine(((i + 1) * width / MinesweeperModel.dimension).toFloat(), (j * height / MinesweeperModel.dimension).toFloat(),
            (i * width / MinesweeperModel.dimension).toFloat(), ((j + 1) * height / MinesweeperModel.dimension).toFloat(), paintLine)
        paintLine.color = Color.BLACK
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if(event?.action == MotionEvent.ACTION_DOWN){
            val tX = event.x.toInt()/ (width/MinesweeperModel.dimension)
            val tY = event.y.toInt()/ (height/MinesweeperModel.dimension)


            if (tX < MinesweeperModel.dimension && tY < MinesweeperModel.dimension) {
                if((context as GamePlayActivity).flagsOn()){
                    MinesweeperModel.setFlagged(tX,tY)
                }else {
                    MinesweeperModel.setClicked(tX, tY)
                }
                invalidate()
            }
        }

        return true
    }

}