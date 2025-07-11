package com.example.clothesadvisor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.clothesadvisor.viewmodel.WeatherViewModel
import com.example.clothesadvisor.utils.WeatherToClothesMapper

class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var cityEditText: EditText
    private lateinit var fetchButton: Button
    private lateinit var resultTextView: TextView

    // ✅ Bu tür sabitleri genelde BuildConfig ile veya Const file ile yönetiriz ama bu hali şimdilik yeterli:
    private val apiKey = "e1bffbf19999bf4d04fe11ef7a9c0d48"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityEditText = findViewById(R.id.cityEditText)
        fetchButton = findViewById(R.id.fetchButton)
        resultTextView = findViewById(R.id.resultTextView)

        fetchButton.setOnClickListener {
            val city = cityEditText.text.toString().trim()
            if (city.isNotEmpty()) {
                viewModel.fetchWeather(city, apiKey)
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.weatherData.observe(this) { weather ->
            weather?.let {
                val temp = it.main?.temp
                val desc = it.weather.firstOrNull()?.description ?: "-"
                val cityName = it.name ?: "-"
                val suggestion = temp?.let { t -> WeatherToClothesMapper.suggestClothing(t) } ?: "No suggestion available"

                val result = """
                    📍 $cityName
                    🌡️ ${temp ?: "?"}°C - $desc
                    👕 $suggestion
                """.trimIndent()

                resultTextView.text = result
            }
        }

        viewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                resultTextView.text = "⚠️ $it"
            }
        }
    }
}
