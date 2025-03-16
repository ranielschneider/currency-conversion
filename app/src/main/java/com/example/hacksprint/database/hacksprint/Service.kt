package com.example.hacksprint.database.hacksprint


import com.example.hacksprint.Request
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("latest/USD")
    fun getNowLatestUSD(): Call<Request>

    @GET("pair/{base}/{target}")
    fun exchangeMoney(@Path("base") base: String, @Path("target") target: String): Call<Request>
}
