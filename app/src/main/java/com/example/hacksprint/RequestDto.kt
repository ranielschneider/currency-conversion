package com.example.hacksprint

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable

data class RequestDto(

    @SerializedName("USD") val usd: Double,
    @SerializedName("EUR") val eur: Double,
    @SerializedName("BRL") val brl: Double,
    @SerializedName("AED") val aed: Double,
    @SerializedName("GBP") val gbp: Double,

)

data class Request(

    val result: String,
    @SerializedName("time_next_update_utc") val timeNextUpdateUtc: String,
    @SerializedName("time_last_update_utc") val timeLastUpdateUtc: String,
    @SerializedName("base_code") val baseCode: String,
    val rates: RequestDto
)