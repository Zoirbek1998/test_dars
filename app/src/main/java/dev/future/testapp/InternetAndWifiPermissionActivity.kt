package dev.future.testapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import dev.future.testapp.connected.NetworkConnection
import dev.future.testapp.databinding.ActivityInternetAndWifiPermissionBinding

class InternetAndWifiPermissionActivity : AppCompatActivity() {
    lateinit var binding : ActivityInternetAndWifiPermissionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternetAndWifiPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){
                binding.layoutDisConnected.visibility = View.GONE
                binding.layoutConnected.visibility = View.VISIBLE
            }else{
                binding.layoutDisConnected.visibility = View.VISIBLE
                binding.layoutConnected.visibility = View.GONE
            }

        })
    }


}