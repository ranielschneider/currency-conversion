package com.example.hacksprint


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

        // Spinner
        var currencyList = listOf("USD", "EUR", "AUD")
        var typesOfMoneyFrom: Int = 0

        val adapterFrom = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            currencyList
        )

        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerFrom.adapter = adapterFrom
        binding.spinnerFrom.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long,
                ) {
                    typesOfMoneyFrom = p2
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    binding.spinnerFrom.getItemAtPosition(0)
                }
            }

        var typesOfMoneyTo: Int = 0

        val adapterTo = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            currencyList
        )

        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerTo.adapter = adapterTo
        binding.spinnerTo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long,
                ) {
                    typesOfMoneyTo = p2
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    binding.spinnerTo.getItemAtPosition(0)
                }
            }


        // Mostra os dados do ViewModel
        viewModel.requestData.observe(this) { request ->
            request?.let {
                println("UI Update: $it") // Log para debug

                currencyList = it.conversionRates.keys.toList()

                updateSpinners(currencyList)
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

        binding.buttonSwap.setOnClickListener {
            val quantityOfMoney = binding.textAmountFrom.editText?.text.toString().toDouble()
            val baseMoney = currencyList[typesOfMoneyFrom]
            val targetMoney = currencyList[typesOfMoneyTo]

            viewModel.exchangeMoney(baseMoney, targetMoney, quantityOfMoney) { result ->
                binding.textAmountTo.setText(result.toString())
            }
        }

    }

    private fun updateSpinners(currencyList: List<String>) {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            currencyList
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter
    }


}

