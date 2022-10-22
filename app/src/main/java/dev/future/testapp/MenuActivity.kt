package dev.future.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import dev.future.testapp.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val items = listOf("Andijon", "Fargona", "Namangan", "Toshkent", "Samarqand")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.dropdownField.setAdapter(adapter)

        binding.dropdownField.setOnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
        }


        emailFocusListener()
        passwordFocusListener()
        phoneFocusListener()

        binding.submit.setOnClickListener {
            submitFrom()
        }

    }

    private fun submitFrom() {
        binding.emailContainer.helperText = valideEmail()
        binding.passwordContainer.helperText = validePassword()
        binding.phoneContainer.helperText = validePhone()


        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validPhone = binding.phoneContainer.helperText == null

        if (validEmail && validPhone && validPassword) {
            resetFrom()
        } else {
            invalidFrom()
        }
    }

    private fun invalidFrom() {
        var message = ""
        if (binding.emailContainer.helperText == null)
            message += "\n\nEmail: " + binding.emailContainer.helperText
        if (binding.passwordContainer.helperText == null) {
            message += "\n\nPassword: " + binding.passwordContainer.helperText
        }
        if (binding.phoneContainer.helperText == null) {
            message += "\n\nPhone: " + binding.phoneContainer.helperText
        }

        AlertDialog.Builder(this)
            .setTitle("Invalid From")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->

            }
            .show()

    }

    private fun resetFrom() {
        var message = "Email: " + binding.emailEditText.text
        message += "\nPassword: " + binding.passwordEditText.text
        message += "\nPhone: " + binding.phoneEditText.text

        AlertDialog.Builder(this)
            .setTitle("From submitted")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                binding.emailEditText.text = null
                binding.passwordEditText.text = null
                binding.phoneEditText.text = null


                binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
                binding.phoneContainer.helperText = getString(R.string.required)
            }
            .show()

    }

    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.emailContainer.helperText = valideEmail()
            }
        }

    }


    private fun valideEmail(): CharSequence? {
        val emailText = binding.emailEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email"
        }


        return null
    }


    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.emailContainer.helperText = validePassword()
            }
        }

    }

    private fun validePassword(): CharSequence? {
        val passwordText = binding.emailEditText.text.toString()
        if (passwordText.length > 8) {
            return "Minimum 8 Character Password"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
//            (1 ta bosh harfdan iborat boʻlishi kerak)
            return "Must Countain 1 Upper-case Character"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Must Countain 1 Lower-case Character"
        }
        if (!passwordText.matches(".*[@#\$%&+=].*".toRegex())) {
            return "Must Countain 1 Spacial Character"
        }
        return null
    }


    private fun phoneFocusListener() {
        binding.phoneEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.phoneContainer.helperText = validePhone()
            }
        }

    }

    private fun validePhone(): CharSequence? {
        val phoneText = binding.phoneEditText.text.toString()
        if (!phoneText.matches(".*[0-9].*".toRegex())) {
            return "Must be 10 Digits"
        }
        if (phoneText.length != 10) {
            return "Must be 10 Digits"
        }


        return null
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_menu -> Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show()
            R.id.nav_calendar -> startActivity(Intent(this,TimeActivity::class.java))

            R.id.nav_search -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()

        }

        return super.onOptionsItemSelected(item)

    }


}


