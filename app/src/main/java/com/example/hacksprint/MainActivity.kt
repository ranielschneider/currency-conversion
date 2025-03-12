package com.example.hacksprint

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hacksprint.database.hacksprint.ApiService
import com.example.hacksprint.database.hacksprint.RetrofitClient
import com.example.hacksprint.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButtonToggleGroup

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val apiService = RetrofitClient.retrofitInstancce.create(ApiService::class.java)
        apiService.getNowLatestUSD()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        toggleGroup.addOnButtonCheckedListener(object :
            MaterialButtonToggleGroup.OnButtonCheckedListener {
            override fun onButtonChecked(
                group: MaterialButtonToggleGroup,
                checkedId: Int,
                isChecked: Boolean
            ) {
                when (checkedId) {
                    R.id.buttonConverter -> {
                        if (isChecked) {
                        } else {
                        }
                    }

                    R.id.buttonGraphic -> {
                        if (isChecked) {
                        } else {
                        }
                    }
                }
            }
        })
    }
}


