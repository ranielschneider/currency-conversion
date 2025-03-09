package com.example.hacksprint

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hacksprint.databinding.ActivityMainBinding
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCurrencyData().start()

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun fetchCurrencyData(): Thread
    {
        return Thread{

            val url = URL("https://v6.exchangerate-api.com/v6/df2b2aa22518deb1608b2694/latest/USD")
            val connection = url.openConnection() as HttpURLConnection

            if (connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                println(inputSystem.toString())
            }
            else
            {
                binding.textAmountFrom.text = getString(R.string.failed_connection)
            }
        }
    }
}