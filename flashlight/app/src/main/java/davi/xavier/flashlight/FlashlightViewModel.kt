package davi.xavier.flashlight

import androidx.lifecycle.ViewModel

class FlashlightViewModel : ViewModel() {
    private var toggle = Toggle.OFF
    private var auto = false
    
    fun getToggle(): Toggle {
        return toggle
    }
    
    fun toggle(): Toggle {
        toggle = when (toggle) {
            Toggle.ON -> Toggle.BLINKING
            Toggle.OFF -> Toggle.ON
            else -> Toggle.OFF
        }
        
        return toggle
    }
    
    fun setToggle(toggle: Toggle) {
        this.toggle = toggle
    }
    
    fun getAuto(): Boolean {
        return auto
    }
    
    fun toggleAuto(): Boolean {
        auto = !auto
        return auto
    }
}
