package com.example.hacksprint


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import com.example.hacksprint.database.hacksprint.ApiService
import com.example.hacksprint.database.hacksprint.RetrofitClient
import com.example.hacksprint.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButtonToggleGroup


data class CurrencyItem(
    val code: String,
    val flagResId: Int)

class CurrencySpinnerAdapter(
    context: Context,
    private val currencyList: List<CurrencyItem>
) : ArrayAdapter<CurrencyItem>(context, 0, currencyList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = currencyList[position]
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)

        val imageViewFlag = view.findViewById<ImageView>(R.id.imageViewFlag)
        val textViewCurrency = view.findViewById<TextView>(R.id.textViewCurrency)

        imageViewFlag.setImageResource(item.flagResId)
        textViewCurrency.text = item.code  // or item.name if you want the full name

        return view
    }
}


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner

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

        // Spinner
        val arrayOfMoney = arrayOf("USD","EUR","JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "MXN", "NZD", "SGD", "HKD", "NOK", "KRW", "TRY", "IND", "RUB", "BRL", "ZAR")
        var typesOfMoneyFrom: Int = 0

        val adapterFrom = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOfMoney
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
            arrayOfMoney
        )

        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerTo.adapter= adapterTo
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

                val currencyList = it.conversionRates.keys.toList()

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

    private val currencyList = listOf(
        CurrencyItem("USD", R.drawable.us_usd),
        CurrencyItem("EUR", R.drawable.eur_eur),
        CurrencyItem("JPY", R.drawable.japan_jpy),
        CurrencyItem("GBP", R.drawable.uk_gbp),
        CurrencyItem("AUD", R.drawable.australia_aud),
        CurrencyItem("CAD", R.drawable.canada_cad),
        CurrencyItem("CHF", R.drawable.switzerland_chf),
        CurrencyItem("CNY", R.drawable.china_cny),
        CurrencyItem("SEK", R.drawable.sweden_sek),
        CurrencyItem("MXN", R.drawable.mexico_mxn),
        CurrencyItem("NZD", R.drawable.newzealand_nzd),
        CurrencyItem("SGD", R.drawable.singapore_sgd),
        CurrencyItem("HKD", R.drawable.hongkong_hkd),
        CurrencyItem("NOK", R.drawable.norway_nok),
        CurrencyItem("KRW", R.drawable.southkorea_krw),
        CurrencyItem("TRY", R.drawable.turkey_try),
        CurrencyItem("INR", R.drawable.india_inr),
        CurrencyItem("RUB", R.drawable.russia_rub),
        CurrencyItem("BRL", R.drawable.brazil_brl),
        CurrencyItem("ZAR", R.drawable.southafrica_zar),

        )

}

