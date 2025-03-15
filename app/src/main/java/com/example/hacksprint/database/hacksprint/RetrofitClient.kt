package com.example.hacksprint.database.hacksprint

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "df2b2aa22518deb1608b2694"
private const val BASE_URL: String = "https://v6.exchangerate-api.com/v6/$API_KEY/"

object RetrofitClient {

    private val httpClient: OkHttpClient
        get() {
            val clientBuilder = OkHttpClient.Builder()
            return clientBuilder.build()
        }

    val retrofitInstance: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
