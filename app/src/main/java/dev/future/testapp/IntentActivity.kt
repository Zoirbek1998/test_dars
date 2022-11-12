package dev.future.testapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import dev.future.testapp.databinding.ActivityIntentBinding

class IntentActivity : AppCompatActivity() {
    lateinit var binding : ActivityIntentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val name = intent.getStringExtra("name")
        val subTitle = intent.getStringExtra("subtitle")
        val image = intent.getIntExtra("image",R.drawable.f)

        binding.name.text = name
        binding.subTitle.text = subTitle
        binding.image.setImageResource(image)

        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val saveString = sharedPreferences.getString("String_key", null)
        val saveBoolean = sharedPreferences.getBoolean("boolean_key", false)
        

    }
}