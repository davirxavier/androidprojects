package davi.xavier.diceroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import davi.xavier.diceroller.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var runnable: Runnable? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.rolarButton.setOnClickListener { 
            onRoll()
        }
    }
    
    fun onRoll() {
        if (runnable != null)
            return
        
        val list = ArrayList<Int>();
        var counter = 0
        runnable = Runnable {
            counter++
            var rand = Random.nextInt(1, 7)
            while (counter < 6 && list.contains(rand))
            {
                rand = Random.nextInt(1, 7)
            }
            list.add(rand)
            
            binding.dadoImage.setImageResource(
                when (rand) {
                    1 -> R.drawable.dice1
                    2 -> R.drawable.dice2
                    3 -> R.drawable.dice3
                    4 -> R.drawable.dice4
                    5 -> R.drawable.dice5
                    else -> R.drawable.dice6
                })

            if (counter == 6)
            {
                runnable = null
                return@Runnable
            }
            binding.dadoImage.postDelayed(runnable, 120)
        }
        binding.dadoImage.postDelayed(runnable, 120)
    }
}
