package com.example.hacksprint.database.hacksprint

import com.example.hacksprint.Request
import com.example.hacksprint.database.hacksprint.RetrofitClient.retrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiRepository {

    private val apiService: ApiService = retrofitInstance.create(ApiService::class.java)

    fun getRequest(callback: (Request?) -> Unit) {
        apiService.getNowLatestUSD().enqueue(object: Callback<Request>{
            override fun onResponse(call: Call<Request>, response: Response<Request>) {
                if (response.isSuccessful) {
                    println("API Response: ${response.body()}") // Log the response
                    val request = response.body()
                    callback(request)
                } else {
                    println("API Error: ${response.errorBody()?.string()}") // Log the error
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Request>, t: Throwable) {
                println("API Failure: ${t.message}") // Log the failure
                callback(null)
            }
        })
    }
}