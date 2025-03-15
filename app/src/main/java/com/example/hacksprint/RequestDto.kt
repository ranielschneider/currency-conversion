package com.example.hacksprint

import com.google.gson.annotations.SerializedName

data class Request(
    val result: String,
    @SerializedName("time_next_update_utc") val timeNextUpdateUtc: String,
    @SerializedName("time_last_update_utc") val timeLastUpdateUtc: String,
    @SerializedName("base_code") val baseCode: String,
    @SerializedName("conversion_rates") val conversionRates: Map<String, Double>,
    @SerializedName("conversion_rate") val rateOfConversion: Double,
)
