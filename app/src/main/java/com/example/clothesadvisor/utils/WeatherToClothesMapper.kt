package com.example.clothesadvisor.utils

object WeatherToClothesMapper {
    fun suggestClothing(temp: Double): String {
        return when {
            temp >= 30 -> "Kısa tişört ve şort giyebilirsin ☀️"
            temp in 20.0..29.9 -> "Tişört ve ince ceket yeterli olabilir 🌤️"
            temp in 10.0..19.9 -> "Uzun kollu ve mont önerilir 🌥️"
            temp < 10 -> "Kalın mont, atkı, eldiven unutma ❄️"
            else -> "Kıyafet önerisi yok 🤷"
        }
    }
}
