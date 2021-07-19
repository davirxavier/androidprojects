package davi.xavier.flashlight

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import davi.xavier.flashlight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FlashlightViewModel
    private var animationShow: ViewPropertyAnimator? = null
    private var animationHide: ViewPropertyAnimator? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this).get(FlashlightViewModel::class.java)
        
        binding.toggleButton.setOnClickListener { onToggle(viewModel.toggle()) }
        onToggle(viewModel.getToggle())
    }
    
    fun onToggle(toggle: Toggle) {
        if (toggle == Toggle.BLINKING) {
            var rShow = Runnable {  }
            var rHide = Runnable {  }

            rShow = Runnable {
                binding.toggleButton.apply {
                    alpha = 0f

                    animationShow = animate()
                        .alpha(1f)
                        .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
                        .withEndAction(rHide)
                        .setListener(null)
                }
            }

            rHide = Runnable {
                binding.toggleButton.apply {
                    alpha = 1f

                    animationHide = animate()
                        .alpha(0f)
                        .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
                        .withEndAction(rShow)
                        .setListener(null)
                }
            }
            rHide.run()
        } else {
            animationHide?.cancel()
            animationShow?.cancel()
            
            binding.toggleButton.alpha = 1f
        }

        val color = when (toggle) {
            Toggle.ON, Toggle.BLINKING -> R.color.colorPrimaryDark
            else -> R.color.colorAccent
        }
        
        binding.toggleButton.backgroundTintList = AppCompatResources.getColorStateList(this, color)
    }
}
