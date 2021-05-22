package davi.xavier.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import davi.xavier.tictactoe.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var currentTurn = 1
    var startingTurn = 1
    val markArr: Array<IntArray> = arrayOf(intArrayOf(0, 0, 0), intArrayOf(0, 0, 0), intArrayOf(0, 0, 0))
    var checkedCount = 0
    var currentWon = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.randoButton.setOnClickListener { randomizeLayout() }
        binding.resetButton.setOnClickListener { restartGame() }
        
        restartGame()
    }
    
    fun onClickImg(img: ImageView, i: Int, j: Int) {
        val im = i-1
        val jm = j-1
        
        if (markArr[im][jm] != 0 || currentWon)
            return

        val oldCurrentTurn = currentTurn
        markArr[im][jm] = currentTurn
        checkedCount++
        if (currentTurn == 1)
        {
            img.setImageResource(R.drawable.x)
            currentTurn = 2
        }
        else
        {
            img.setImageResource(R.drawable.o)
            currentTurn = 1
        }
        val char = NumChar.getByValue(currentTurn).char
        binding.turnText.text = "Turn: $char"

        val won = isWin()
        if (won == WinEnum.WIN)
        {
            currentWon = true
            binding.turnText.text = "Player " + NumChar.getByValue(oldCurrentTurn).char + " won!"
        }
        else if (won == WinEnum.DRAW)
        {
            currentWon = true
            binding.turnText.text = "Draw!"
        }
    }
    
    private fun isWin(): WinEnum {
        if (checkedCount == 9)
            return WinEnum.DRAW
        
        for (i in intArrayOf(0, 1, 2)) {
            var countX = 0
            var countY = 0
            var countDiagL = 0
            var countDiagR = 0
            for (j in intArrayOf(1, 2)) {
                if (markArr[i][j] != 0 && markArr[i][j] == markArr[i][j-1])
                {
                    countX++
                }
                if (markArr[j][i] != 0 && markArr[j][i] == markArr[j-1][i])
                {
                    countY++
                }
                if (markArr[j][j] != 0 && markArr[j][j] == markArr[j-1][j-1])
                {
                    countDiagL++
                }
                if (markArr[j][2-j] != 0 && markArr[j][2-j] == markArr[j-1][3-j])
                {
                    countDiagR++
                }
            }
            
            if (countX > 1 || countY > 1 || countDiagL > 1 || countDiagR > 1) {
                return WinEnum.WIN
            }
        }
        
        return WinEnum.NO_WIN
    }
    
    fun restartGame() {
        val arr: IntArray = intArrayOf(1, 2, 3)
        
        for (i in arr) {
            for (j in arr) {
                val img = getImgView(i, j)
                img.setImageResource(0)
                img.setOnClickListener {onClickImg(img, i, j)}

                markArr[i-1][j-1] = 0
            }
        }

        checkedCount = 0
        currentWon = false
        startingTurn = Random.nextInt(1, 3)
        currentTurn = startingTurn
        binding.turnText.setText("Turn: " + NumChar.getByValue(currentTurn).char)
    }
    
    fun randomizeLayout() {
        val arr: IntArray = intArrayOf(1, 2, 3)
        
        for (i in arr) {
            for (j in arr) {
                getImgView(i, j).setImageResource(when (Random.nextInt(1, 3)) {
                    1 -> R.drawable.o
                    else -> R.drawable.x
                })
            }
        }
    }
    
    fun getImgView(i: Int, j: Int): ImageView {
        return findViewById(resources.getIdentifier("img$i$j", "id", packageName));
    }
    
    private enum class NumChar(val char: Char, val value: Int) {
        X('X', 1), O('O', 2);
        
        companion object {
            fun getByValue(value: Int): NumChar {
                return values().first { e -> e.value == value }
            }
        }
    }
    
    private enum class WinEnum(val value: Int) {
        DRAW(2), WIN(1), NO_WIN(0)
    }
}
