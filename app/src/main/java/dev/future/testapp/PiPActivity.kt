package dev.future.testapp

import android.app.PictureInPictureParams
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.Display
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import dev.future.testapp.databinding.ActivityPiPactivityBinding

class PiPActivity : AppCompatActivity() {
    lateinit var binding : ActivityPiPactivityBinding
    @RequiresApi(Build.VERSION_CODES.O)
    var pictureInPictureParamsBuilder = PictureInPictureParams.Builder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityPiPactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabBtn.setOnClickListener {
            startPictureInPictureFeature()
        }

    }

    private fun startPictureInPictureFeature() {
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width: Int = size.x + size.x / 2
        val height: Int = size.y
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val aspecRatio = Rational(width,height)
            pictureInPictureParamsBuilder.setAspectRatio(aspecRatio).build()
            enterPictureInPictureMode(pictureInPictureParamsBuilder.build())
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserLeaveHint() {
//        super.onUserLeaveHint()
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width: Int = size.x + size.x / 2
        val height: Int = size.y
        if (!isInPictureInPictureMode){
            val aspectRation = Rational(width,height)
            pictureInPictureParamsBuilder.setAspectRatio(aspectRation)
            enterPictureInPictureMode(pictureInPictureParamsBuilder.build())
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        if (isInPictureInPictureMode){
            binding.fabBtn.visibility = View.GONE
            binding.tvText.visibility = View.VISIBLE
        }else{
            binding.fabBtn.visibility = View.VISIBLE
            binding.tvText.visibility = View.GONE
        }
    }


}