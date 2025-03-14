package com.example.hacksprint


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

//>>>>>>> ff68f135401c464d604396531c0747add8c28952

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ViewModel para mostrar os dados da API na tela
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
       apiService.getNowLatestUSD()

        // Inicializa o View Binding
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
        viewModel.requestData.observe(this) {request ->
            if (request != null) {
                // Atualiza a UI com os dados

                println("UI Update: $request") // Log the data

                binding.resultTextView.text = request.result
                binding.baseCodeTextView.text = request.baseCode
                binding.usdRateTextView.text = request.conversionRates.usd.toString()
                binding.eurRateTextView.text = request.conversionRates.eur.toString()

            }
        }

        // Mostra possível mensagem de erro
        viewModel.errorMessage.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                println("UI Error: $error") // Log the error
                binding.errorTextView.text = error // Mostra a mensagem em uma TextView
            }
        }

        // Puxa os dados
        viewModel.fetchData()
    }
}

