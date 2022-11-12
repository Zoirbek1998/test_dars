package dev.future.testapp.services

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.future.testapp.R
import dev.future.testapp.databinding.ActivityServicesBinding
import dev.future.testapp.floatingWindow.Common
import dev.future.testapp.floatingWindow.Common.Companion.currDes
import dev.future.testapp.floatingWindow.FloatingWindowApp
import kotlin.math.roundToInt

class ServicesActivity : AppCompatActivity() {
    lateinit var binding: ActivityServicesBinding
    var timerStarted = false
    lateinit var serviceIntent: Intent
    var time = 0.0

    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (isServiceRunning()){
            stopService(Intent(this,FloatingWindowApp::class.java))

        }




//        isServiceRunning()
//        requestPermissions()
//        checkAndroidVersion()

        binding.btnFloating.setOnClickListener {
            if (checkOverlayPermission()){
                startService(Intent(this,FloatingWindowApp::class.java))
                finish()
            }else{
                requestFloatingWindowPermission()
            }
        }



        binding.startStopButton.setOnClickListener { startStopTimer() }
        binding.resetButton.setOnClickListener { resetTimer() }

        serviceIntent = Intent(applicationContext, TimerServices::class.java)
        registerReceiver(updateTime, IntentFilter(TimerServices.TIMER_UPATED))




    }

    private fun isServiceRunning(): Boolean {

        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (FloatingWindowApp::class.java.name == service.service.className) {
                return true
            }

        }

        return false
    }

    private fun requestFloatingWindowPermission(){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle("Screen Overlay Permission Needed")
        builder.setMessage("Enable 'Display over this App' from settings")
        builder.setPositiveButton("Open Setting",DialogInterface.OnClickListener{dialog,which ->
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName"),
            )
            startActivityForResult(intent, RESULT_OK)
        })
        dialog = builder.create()
        dialog.show()

    }

    private fun checkOverlayPermission():Boolean{
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            Settings.canDrawOverlays(this)
        }
        else return true
    }



















    private fun resetTimer() {
        stopTimer()
        time = 0.0
        binding.tvTime.text = getTimeStringFromDouble(time)
    }

    private fun startStopTimer() {

        if (timerStarted)
            stopTimer()
        else
            startTimer()

    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerServices.TIMER_EXTRA, time)
        startService(serviceIntent)
        binding.startStopButton.text = "Stop"
        binding.startStopButton.icon = getDrawable(R.drawable.ic_pause)
        timerStarted = true
    }

    private fun stopTimer() {
        stopService(serviceIntent)
        binding.startStopButton.text = "Stop"
        binding.startStopButton.icon = getDrawable(R.drawable.ic_pause)
        timerStarted = false

        var minutSumm = (time / 60) * 5000
        Toast.makeText(this, "${minutSumm}", Toast.LENGTH_SHORT).show()
        if (time > 300) {


        } else {

        }

    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerServices.TIMER_EXTRA, 0.0)
            binding.tvTime.text = getTimeStringFromDouble(time)
            currDes = getTimeStringFromDouble(time)

            val intent = Intent("ActiveTime")
            intent.putExtra("time",time)
            sendBroadcast(intent)

        }

    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Int, minutes: Int, seconds: Int): String =
        String.format("%02d:%02d:%02d", hours, minutes, seconds)


}