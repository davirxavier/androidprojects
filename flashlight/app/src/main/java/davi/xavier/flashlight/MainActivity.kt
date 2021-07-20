package davi.xavier.flashlight

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewPropertyAnimator
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import davi.xavier.flashlight.databinding.ActivityMainBinding
import java.lang.Exception

const val MAX_LUX_VALUE = 200

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FlashlightViewModel
    private lateinit var cameraManager: CameraManager
    private var animationShow: ViewPropertyAnimator? = null
    private var animationHide: ViewPropertyAnimator? = null
    
    private lateinit var sensorManager: SensorManager
    private lateinit var lightSensor: Sensor
    
    private var isFlashOn = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        setFlash(false)
        
        viewModel = ViewModelProvider(this).get(FlashlightViewModel::class.java)
        
        binding.toggleButton.setOnClickListener { onToggle(viewModel.toggle()) }
        onToggle(viewModel.getToggle())
        
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        
        binding.autoButton.setOnClickListener{ onToggleAuto(viewModel.toggleAuto()) }
        onToggleAuto(viewModel.getAuto())
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            setFlash(false)
        }
    }

    fun onToggle(toggle: Toggle) {
        if (toggle == Toggle.BLINKING) {
            startFlashAnimation()
        } else {
            stopFlashAnimation()

            binding.toggleButton.alpha = 1f

            setFlash(when(toggle) {
                Toggle.ON -> true
                else -> false
            })
        }

        val color = when (toggle) {
            Toggle.ON, Toggle.BLINKING -> R.color.colorPrimaryDark
            else -> R.color.colorAccent
        }

        binding.toggleButton.backgroundTintList = AppCompatResources.getColorStateList(this, color)
    }
    
    private fun startFlashAnimation() {
        var rShow = Runnable {  }
        var rHide = Runnable {  }

        rShow = Runnable {
            binding.toggleButton.apply {
                alpha = 0f
                setFlash(false)

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
                setFlash(true)

                animationHide = animate()
                    .alpha(0f)
                    .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
                    .withEndAction(rShow)
                    .setListener(null)
            }
        }
        rHide.run()
    }
    
    private fun stopFlashAnimation() {
        animationHide?.let { 
            it.cancel()
            animationHide = null
        }
        animationShow?.let { 
            it.cancel()
            animationShow = null
        }
    }
    
    fun setFlash(on: Boolean) {
        if (isFlashOn != on) {
            try {
                val ids = cameraManager.cameraIdList
                ids.forEach {
                    try {
                        cameraManager.setTorchMode(it, on)
                    } catch (ignored: Exception) { }
                }
            } catch (ignored: CameraAccessException) {}
            isFlashOn = on
        }
    }
    
    private fun onToggleAuto(auto: Boolean) {
        binding.toggleButton.isEnabled = !auto
        if (auto) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
            
            viewModel.setToggle(Toggle.OFF)
            onToggle(Toggle.OFF)
        } else if (viewModel.getToggle() == Toggle.OFF) {
            sensorManager.unregisterListener(this, lightSensor)
            setFlash(false)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { 
            val light = event.values[0]
            setFlash(light < MAX_LUX_VALUE)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
