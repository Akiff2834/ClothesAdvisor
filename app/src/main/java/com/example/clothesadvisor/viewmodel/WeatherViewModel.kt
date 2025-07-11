package com.example.clothesadvisor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesadvisor.model.WeatherResponse
import com.example.clothesadvisor.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> = _weatherData

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchWeather(cityName: String, apiKey: String) {
        val call = ApiClient.weatherApiService.getWeatherByCity(cityName, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    _weatherData.value = response.body()
                    _error.value = null
                } else {
                    _error.value = "API error: ${response.message()}"
                    _weatherData.value = null
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _error.value = "Network error: ${t.message}"
                _weatherData.value = null
            }
        })
    }
}
