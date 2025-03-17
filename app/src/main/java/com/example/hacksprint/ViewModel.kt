package com.example.hacksprint

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hacksprint.database.hacksprint.ApiRepository

// Will expose the data to the UI (MainActivity). It will use the Repository.kt to fetch data

class MainViewModel : ViewModel() {

    private val repository = ApiRepository()
    val requestData = MutableLiveData<Request>()
    val errorMessage = MutableLiveData<String>()

    fun fetchData() {
        repository.getRequest { request ->
            if (request != null) {
                println("ViewModel Data: $request") // Log the data
                requestData.postValue(request)
            } else {
                println("ViewModel Error: No data received") // Log the error
                errorMessage.postValue("Failed to fetch data")
            }
        }
    }

    fun exchangeMoney(base: String, target: String, callback: (Double) -> Unit) {
        repository.getConversionRate(base, target) { request ->
            if (request != null) {
                val rate = request.rateOfConversion
                callback(rate)
            } else {
                errorMessage.postValue("Failed to fetch data")
                callback(0.0)
            }
        }
    }

}