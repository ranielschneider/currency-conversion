package com.example.hacksprint

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hacksprint.database.hacksprint.ApiRepository

// Will expose the data to the UI (MainActivity). It will use the Repository.kt to fetch data

class MainViewModel: ViewModel() {

    private val repository = ApiRepository()
    val requestData = MutableLiveData<Request>()
    val errorMessage = MutableLiveData<String>()

    fun fetchData() {
        repository.getRequest { request ->
            if (request != null) {
                requestData.postValue(request)
            } else {
                errorMessage.postValue("Failed to fetch data")
            }
        }
    }
}