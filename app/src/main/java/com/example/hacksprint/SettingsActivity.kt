package com.example.hacksprint

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hacksprint.databinding.SettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = SettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up button listeners
        binding.darkButton.setOnClickListener {
            ThemeHelper.setTheme("dark")
            ThemeHelper.saveTheme(this, "dark")
            recreate() // Recreate the activity to apply the new theme
        }

        binding.lightButton.setOnClickListener {
            ThemeHelper.setTheme("light")
            ThemeHelper.saveTheme(this, "light")
            recreate() // Recreate the activity to apply the new theme
        }

    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher
        return true
    }
}
