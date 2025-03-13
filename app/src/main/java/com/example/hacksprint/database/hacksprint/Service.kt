package com.example.hacksprint.database.hacksprint


import com.example.hacksprint.Request
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("GET https://v6.exchangerate-api.com/v6/")
    fun getNowLatestUSD(): Call<Request>
}
