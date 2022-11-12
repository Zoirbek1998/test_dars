package dev.future.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import dev.future.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.webView.webViewClient = WebViewClient()
        binding.webView.webViewClient = WebViewClient()

        binding.webView.apply {
            loadUrl("https://kun.uz")
            settings.javaScriptEnabled = true
        }

    }
}