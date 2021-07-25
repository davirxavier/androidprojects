package davi.xavier.drugstores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import davi.xavier.drugstores.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var drugstores = Drugstores.drugstores
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { onMapReady(it) }
    }
    
    fun onMapReady(map: GoogleMap) {
        drugstores.forEach { 
            map.addMarker(MarkerOptions()
                .position(it.pos)
                .title(it.name))
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-3.738335, -38.525233), 12.0F))
    }
}
