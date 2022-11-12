package dev.future.testapp.floatingWindow

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import dev.future.testapp.R
import dev.future.testapp.services.ServicesActivity
import kotlin.math.roundToInt

class FloatingWindowApp : Service() {

    lateinit var floatView: ViewGroup
    lateinit var floatingWindowLayoutParams: WindowManager.LayoutParams
    var LAYOUT_TYPE: Int? = null
    lateinit var windowManager: WindowManager

    lateinit var serviceIntent: Intent
    var time = 0.0
    lateinit var btnMax: Button


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        val locationReceiver = FloatingWindowApp.LocationBrodcastReceiver()
        val intentFilter = IntentFilter("ActiveTime")
        registerReceiver(locationReceiver, intentFilter)

        val matrecs = applicationContext.resources.displayMetrics
        val width = matrecs.widthPixels
        val height = matrecs.heightPixels

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val inflater = baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        floatView = inflater.inflate(R.layout.floating_layout, null) as ViewGroup

        btnMax = floatView.findViewById(R.id.btn_max)
        txtDec = floatView.findViewById(R.id.tvTimeFloating)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_TYPE = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            LAYOUT_TYPE = WindowManager.LayoutParams.TYPE_TOAST
        }

        floatingWindowLayoutParams = WindowManager.LayoutParams(
            (width * 0.55f).toInt(),
            (height * 0.20f).toInt(),
            LAYOUT_TYPE!!,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        floatingWindowLayoutParams.gravity = Gravity.CENTER
        floatingWindowLayoutParams.x = 0
        floatingWindowLayoutParams.y = 0
        windowManager.addView(floatView, floatingWindowLayoutParams)



        btnMax.setOnClickListener {
            Toast.makeText(this, "Bosulmoqda", Toast.LENGTH_SHORT).show()
            stopSelf()
            windowManager.removeView(floatView)

            val back = Intent(this@FloatingWindowApp, ServicesActivity::class.java)
            back.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(back)
        }


        floatView.setOnTouchListener(object : View.OnTouchListener {

            val updateFloatWindowLayoutParams = floatingWindowLayoutParams

            var x = 0.0
            var y = 0.0
            var px = 0.0
            var py = 0.0

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        x = updateFloatWindowLayoutParams.x.toDouble()
                        y = updateFloatWindowLayoutParams.y.toDouble()

                        px = event.rawX.toDouble()
                        py = event.rawY.toDouble()
                    }

                    MotionEvent.ACTION_MOVE ->{
                        updateFloatWindowLayoutParams.x = (x + event.rawX - px).toInt()
                        updateFloatWindowLayoutParams.y = (y + event.rawY - py).toInt()

                        windowManager.updateViewLayout(floatView,updateFloatWindowLayoutParams)
                    }
                }
                return false
            }

        })
    }


    class LocationBrodcastReceiver : BroadcastReceiver() {


        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action.equals("ActiveTime")) {
                time = intent.getDoubleExtra("time", 0.0)!!
            }
//
//            Toast.makeText(context, time.toString(), Toast.LENGTH_SHORT).show()

            txtDec.text = getTimeStringFromDouble(time)


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


    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
        windowManager.removeView(floatView)
    }

    companion object {
        var time = 0.0

        lateinit var txtDec: TextView

    }

}