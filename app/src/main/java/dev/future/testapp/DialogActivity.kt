package dev.future.testapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DialogActivity : AppCompatActivity() {

    var btnDialog: Button? = null
    lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)


        btnDialog = findViewById(R.id.btn_dialog)
        builder = AlertDialog.Builder(this)

        btnDialog!!.setOnClickListener {
            builder.setTitle("Salom")
                .setMessage("Qaleszlar yahshimi hayot")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialogInflater, it ->
                    finish()
                }
                .setNegativeButton("No") { dialogInflater, it ->
                    dialogInflater.cancel()
                }
                .setNeutralButton("Help") { dialogInflater, it ->
                    Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show()

                }
                .show()
        }

    }
}