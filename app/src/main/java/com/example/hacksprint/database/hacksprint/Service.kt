package com.example.hacksprint.database.hacksprint

import com.example.hacksprint.R
import com.example.hacksprint.Request
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.http.GET
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


interface ApiService {

    @GET("GET https://v6.exchangerate-api.com/v6/")
    fun getNowLatestUSD() :Call<List<Unit>>
}

//object Service {

    // API KEY:     val = df2b2aa22518deb1608b2694/latest/USD

//    private val httpClient: OkHttpClient
//        get() {
//
//            val connection = OkHttpClient.Builder()
//
//            if (connection.responseCode == 200) {
//
//                val inputSystem = connection.inputStream
//                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
//                val request = Gson().fromJson(inputStreamReader, Request::class.java)
//                updateUI(request)
//                inputStreamReader.close()
//                inputSystem.close()
//
//            } else {
//
//                binding.textAmountFrom.text = getString(R.string.failed_connection)
//            }
//
//        }
//
// }
