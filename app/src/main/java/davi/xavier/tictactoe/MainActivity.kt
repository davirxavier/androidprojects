package davi.xavier.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import davi.xavier.tictactoe.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var currentTurn = 'X'
    var startingTurn = 1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.randoButton.setOnClickListener { randomizeLayout() }
        binding.resetButton.setOnClickListener { restartGame() }
        
        restartGame()
    }
    
    fun onClickImg(img: ImageView, i: Int, j: Int) {
        if (currentTurn == 'X')
        {
            img.setImageResource(R.drawable.x)
            currentTurn = 'O'
        }
        else
        {
            img.setImageResource(R.drawable.o)
            currentTurn = 'X'
        }
        binding.turnText.setText("Turn: $currentTurn")
        
        val won = isWin(i, j)
    }
    
    fun isWin(i: Int, j: Int): Boolean {
        
        
        return false
    }
    
    fun restartGame() {
        val arr: IntArray = intArrayOf(1, 2, 3)
        
        for (i in arr) {
            for (j in arr) {
                val img = getImgView(i, j)
                img.setImageResource(0)
                img.setOnClickListener {onClickImg(img, i, j)}
            }
        }

        startingTurn = Random.nextInt(1, 3)
        currentTurn = when(startingTurn) {1 -> 'X' else -> 'O'}
        binding.turnText.setText("Turn: $currentTurn")
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
}
