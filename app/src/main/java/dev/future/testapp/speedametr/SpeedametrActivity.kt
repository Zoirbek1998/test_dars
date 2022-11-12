package dev.future.testapp.speedametr

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dev.future.testapp.PiPActivity
import dev.future.testapp.databinding.ActivitySpeedametrBinding
import dev.future.testapp.locationService.SpeedService

class SpeedametrActivity : AppCompatActivity(), LocationListener {


    lateinit var binding: ActivitySpeedametrBinding
    var speed = 0f
    lateinit var serviceSpeed: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeedametrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIntent.setOnClickListener {
            startActivity(Intent(this,PiPActivity::class.java))
        }


//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//            ),
//            0
//        )

        //check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
                1000
            )
        } else {

            //start the program if permission is granted
            doStuff()
        }

        serviceSpeed =
            Intent(applicationContext, SpeedService::class.java)
        registerReceiver(
            updateSpeed,
            IntentFilter(SpeedService.SPEED_UPDADE)
        )


        binding.tvStart.setOnClickListener {
            Intent(applicationContext, SpeedService::class.java).apply {
                action = SpeedService.ACTION_START
                startService(this)
            }

        }

        binding.tvStop.setOnClickListener {
            Intent(applicationContext, SpeedService::class.java).apply {
                action = SpeedService.ACTION_STOP
                startService(this)
            }

            binding.tvSpeed.text = "0 km/h"

        }



    }


    private val updateSpeed: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            var currentSpeed =0f
            if (intent.action.equals(SpeedService.SPEED_UPDADE)) {
                currentSpeed = intent.getFloatExtra(SpeedService.SPEED,0f)!!
                speed = currentSpeed * 3.6f
                Log.e( "onSpeed: ", speed.toString())
                binding.tvSpeed.text = String.format("%.2f", speed) + " km/h"
            }

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doStuff()
            } else {
                finish()
            }
        }
    }
    private fun doStuff() {
        val lm = this.getSystemService(LOCATION_SERVICE) as LocationManager
        if (lm != null) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
            //commented, this is from the old version
            // this.onLocationChanged(null);
        }
        Toast.makeText(this, "Waiting for GPS connection!", Toast.LENGTH_SHORT).show()
    }


    override fun onLocationChanged(location: Location) {

    }


}