package dev.future.testapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.future.testapp.databinding.ActivitySharedPrefBinding

class SharedPrefActivity : AppCompatActivity() {
    lateinit var binding: ActivitySharedPrefBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPrefBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()

        binding.btButtn.setOnClickListener {
            saveData()
        }
    }


    private fun saveData() {
        val insertedText = binding.edtText.text.toString()
        binding.tvText.text = insertedText

        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("String_key", insertedText)
            putBoolean("boolean_key", binding.switch1.isChecked)
        }.apply()

        Toast.makeText(this, "Data save", Toast.LENGTH_SHORT).show()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val saveString = sharedPreferences.getString("String_key", null)
        val saveBoolean = sharedPreferences.getBoolean("boolean_key", false)

        binding.tvText.text = saveString
        binding.switch1.isChecked = saveBoolean
    }

}