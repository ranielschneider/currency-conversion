package com.example.hacksprint


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hacksprint.databinding.ActivityMainBinding
import org.json.JSONArray
import java.net.URL


class CurrencySpinnerAdapter(
    context: Context,
    private val currencyList: List<String>
) : ArrayAdapter<String>(context, 0, currencyList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.spinner_item, parent, false)

        val imageViewFlag = view.findViewById<ImageView>(R.id.imageViewFlag)
        val textViewCurrency = view.findViewById<TextView>(R.id.textViewCurrency)

        val currencyCode = currencyList[position]
        textViewCurrency.text = currencyCode

        Thread {
            try {
                val url = URL("https://restcountries.com/v3.1/currency/$currencyCode")
                val response = url.readText()

                val jsonArray = JSONArray(response)
                val country = jsonArray.getJSONObject(0)
                val flagUrl = country.getJSONObject("flags").getString("png")

                val flagStream = URL(flagUrl).openStream()
                val bitmap = BitmapFactory.decodeStream(flagStream)

                // Atualiza a UI na thread principal
                (context as AppCompatActivity).runOnUiThread {
                    imageViewFlag.setImageBitmap(bitmap)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()

        return view
    }
}


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var currencyList = listOf("USD", "EUR", "AUD")
    private var typesOfMoneyFrom = 0
    private var typesOfMoneyTo = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialização do View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEdgeToEdge()
        makeListeners()
        standardSpinners()

        dataViewModel()
        viewModel.fetchData()
    }

    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun makeListeners() {
        binding.settingsButton.setOnClickListener {
            openSettings()
        }

        binding.buttonSwap.setOnClickListener {
            makeConversion()
        }

        binding.buttonClear.setOnClickListener {
            clearAll()
        }
    }

    // Inicialização dos spinners
    private fun standardSpinners() {
        updateSpinners(currencyList)
    }

    // Dados do ViewModel
    private fun dataViewModel() {
        viewModel.requestData.observe(this) { request ->
            request?.let {
                currencyList = it.conversionRates.keys.toList()
                updateSpinners(currencyList)
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                binding.errorTextView.text = error
            }
        }
    }

    // Atualiza os spinners
    private fun updateSpinners(currencyItems: List<String>) {
        val adapter = CurrencySpinnerAdapter(this, currencyItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter

        binding.spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                typesOfMoneyFrom = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                typesOfMoneyTo = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    // Conversão de moedas
    @SuppressLint("SetTextI18n")
    private fun makeConversion() {
        try {
            val inputText = binding.textAmountFrom.text?.toString()
            val quantity = inputText?.toDouble() ?: 1.0

            val baseCurrency = currencyList[typesOfMoneyFrom]
            val targetCurrency = currencyList[typesOfMoneyTo]

            viewModel.exchangeMoney(baseCurrency, targetCurrency) { result ->
                val convertedValue = result * quantity
                binding.textAmountTo.setText(convertedValue.toString())
                binding.targetRateTextView.text = "$targetCurrency: $result"
            }
            binding.baseRateTextView.text = "$baseCurrency: 1"

        } catch (e: NumberFormatException) {
            binding.errorTextView.text = "Valor inválido"
        }
    }

    // Limpa os campos
    private fun clearAll() {
        binding.textAmountFrom.setText("")
        binding.textAmountTo.setText("")
        binding.baseRateTextView.text = ""
        binding.targetRateTextView.text = ""
        binding.errorTextView.text = ""
    }

    // Abre a tela de configurações
    private fun openSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}