package dev.future.testapp.services

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
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import dev.future.testapp.R
import dev.future.testapp.databinding.ActivityMapServiceBinding
import dev.future.testapp.locationService.LocationService
import kotlinx.coroutines.flow.combine

class MapServiceActivity : AppCompatActivity(), LocationListener {
    lateinit var binding: ActivityMapServiceBinding
    private lateinit var receiver: BroadcastReceiver




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)




        //check permission

        //added because new access fine location policies, imported class..
        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


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

        if (txt>"15"){
            val locationReceiver = LocationBrodcastReceiver()
            val intentFilter = IntentFilter("ACT_LOC")
            registerReceiver(locationReceiver, intentFilter)

            Intent(applicationContext, LocationService::class.java).apply {
                action = LocationService.ACTION_START
                startService(this)
            }
        }else
            if (txt < "15"){
            Intent(applicationContext, LocationService::class.java).apply {
                action = LocationService.ACTION_STOP
                startService(this)
            }
        }

//        binding.btnStart.setOnClickListener {
//
//            val locationReceiver = LocationBrodcastReceiver()
//            val intentFilter = IntentFilter("ACT_LOC")
//            registerReceiver(locationReceiver, intentFilter)
//
//            Intent(applicationContext, LocationService::class.java).apply {
//                action = LocationService.ACTION_START
//                startService(this)
//            }
//        }
        binding.btnStop.setOnClickListener {
            Intent(applicationContext, LocationService::class.java).apply {
                action = LocationService.ACTION_STOP
                startService(this)
            }
        }

        binding.textLocation.text = textKm
        binding.textState.text = textMetr


    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }


    class LocationBrodcastReceiver : BroadcastReceiver() {


        data class latlong(var lat: Double, var long: Double)

        var meter = 0
        var summ = 0
        var latlongList = mutableListOf<latlong>()
        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action.equals("ACT_LOC")) {
                var lat = intent.getStringExtra("latitude")!!.toDouble()
                var long = intent.getStringExtra("longitude")!!.toDouble()

                if (latlongList.size > 0) {
                    meter = SphericalUtil.computeDistanceBetween(
                        LatLng(lat, long),
                        LatLng(latlongList[0].lat, latlongList[0].long),
                    ).toInt()
                    latlongList.clear()
                }
                latlongList.add(latlong(lat, long))

                Log.e("location", "Lat is: ${lat} , Long is: ${long}")

            }
            summ += meter

            var location = String.format("%.1f", summ * 1.0 / 1000) + " km"

            Log.e("location", location + "km")
            Log.e("location", meter.toString() + "Metr")

            textKm = location
            textMetr = meter.toString()

//            Toast.makeText(context, location, Toast.LENGTH_SHORT).show()
//            Toast.makeText(context, meter.toString(), Toast.LENGTH_SHORT).show()



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


    companion object {
        var textKm = "Kilomert"
        var textMetr = "Metr"
        var txt=""
    }

    override fun onLocationChanged(location: Location) {
        var text = findViewById<View>(R.id.textView1) as TextView
        if (location == null) {
            text.text = "-.- km/h"
            txt = "0"
        } else {
            val nCurrentSpeed = location.speed * 3.6f
            text.text = String.format("%.2f", nCurrentSpeed) + " km/h"
            txt = String.format("%.2f", nCurrentSpeed)
        }
    }


}