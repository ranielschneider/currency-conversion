package com.example.hacksprint.database.hacksprint


import com.example.hacksprint.Request
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("https://v6.exchangerate-api.com/v6/df2b2aa22518deb1608b2694/latest/USD")
    fun getNowLatestUSD(): Call<Request>
}
