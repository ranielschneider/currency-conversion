package com.example.hacksprint

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButtonToggleGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val spinnerFrom = findViewById<Spinner>(R.id.spinner_from)

        val arrayOfMoney = arrayOf("USD","EUR","AUD")
        var typesOfMoneyFrom: Int = 0

        val adapterFrom = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOfMoney
        )

        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerFrom.adapter = adapterFrom
        spinnerFrom.onItemSelectedListener =
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
                    spinnerFrom.getItemAtPosition(0)
                }
            }

        val spinnerTo = findViewById<Spinner>(R.id.spinner_to)

        var typesOfMoneyTo: Int = 0

        val adapterTo = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOfMoney
        )

        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerTo.adapter= adapterTo
        spinnerTo.onItemSelectedListener =
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
                    spinnerTo.getItemAtPosition(0)
                }
            }


            val toggleGroup= findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
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
    }
    }


