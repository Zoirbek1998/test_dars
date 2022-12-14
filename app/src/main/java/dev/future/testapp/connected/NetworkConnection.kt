package dev.future.testapp.connected

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData

class NetworkConnection(private val context: Context) : LiveData<Boolean>() {
    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        updateConnection()
        when{
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N->{
                connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallBack())
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP->{
                lollipopNetworkRequest()
            }
            else->{
                context.registerReceiver(
                    networkReceive,
                    IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                )
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkRequest(){
        val requestBuilder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)

        connectivityManager.registerNetworkCallback(
            requestBuilder.build(),
            connectivityManagerCallBack()
        )
    }

    private fun connectivityManagerCallBack(): ConnectivityManager.NetworkCallback{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            networkCallback = object : ConnectivityManager.NetworkCallback(){
                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }
            }
            return networkCallback
        }else{
            throw IllegalAccessError("Error")
        }
    }

    private val networkReceive = object  : BroadcastReceiver(){
        override fun onReceive(context: Context?, internet: Intent?) {
            updateConnection()
        }
    }

    private fun updateConnection(){
        val actiNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(actiNetwork?.isConnected == true)
    }
}