package com.example.hacksprint


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hacksprint.database.hacksprint.ApiService
import com.example.hacksprint.database.hacksprint.RetrofitClient
import com.example.hacksprint.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButtonToggleGroup


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ViewModel para mostrar os dados da API na tela
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
        apiService.getNowLatestUSD()

        // Inicializa o View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
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

        // Mostra os dados do ViewModel
        viewModel.requestData.observe(this) { request ->
            request?.let {
                println("UI Update: $it") // Log para debug

                binding.resultTextView.text = it.result
                binding.baseCodeTextView.text = it.baseCode
                binding.usdRateTextView.text = it.conversionRates.usd.toString()
                binding.eurRateTextView.text = it.conversionRates.eur.toString()
            }
        }

        // Mostra possível mensagem de erro
        viewModel.errorMessage.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                println("UI Error: $error")
                binding.errorTextView.text = error
            }
        }
        // Puxa os dados
        viewModel.fetchData()
    }

}

