package dev.future.testapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class TimerServices: Service() {
    override fun onBind(intent: Intent?): IBinder?  = null

    private val timer =Timer()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val time = intent.getDoubleExtra(TIMER_EXTRA,0.0)
        timer.scheduleAtFixedRate(TimeTask(time),0,1000)
        return START_NOT_STICKY

    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()

    }

    private inner class TimeTask(private var time:Double) : TimerTask()
    {
        override fun run() {
            val intent = Intent(TIMER_UPATED)
            time++
            intent.putExtra(TIMER_EXTRA,time)
            sendBroadcast(intent)
        }

    }

    companion object{
        const val TIMER_UPATED = "timerUpdated"
        const val TIMER_EXTRA = "timerEXTRA"
    }
}