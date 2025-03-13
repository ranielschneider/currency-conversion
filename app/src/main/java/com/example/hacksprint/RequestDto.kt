package com.example.hacksprint

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable

data class RequestDto(

    @SerializedName("USD") val usd: Double,
    @SerializedName("JPY") val jpy: Double,
    @SerializedName("EUR") val eur: Double,
    @SerializedName("BRL") val brl: Double,
    @SerializedName("AUD") val aud: Double,
    @SerializedName("CAD") val cad: Double,
    @SerializedName("CHF") val chf: Double,
    @SerializedName("CNY") val cny: Double,
    @SerializedName("SEK") val sek: Double,
    @SerializedName("MXN") val mxn: Double,
    @SerializedName("AED") val aed: Double,
    @SerializedName("NZD") val nzd: Double,
    @SerializedName("SGD") val sgd: Double,
    @SerializedName("HKD") val hkd: Double,
    @SerializedName("NOK") val nok: Double,
    @SerializedName("KRW") val krw: Double,
    @SerializedName("TRY") val tri: Double,
    @SerializedName("INR") val inr: Double,
    @SerializedName("RUB") val rub: Double,
    @SerializedName("ZAR") val zar: Double,
    @SerializedName("GBP") val gbp: Double

)

data class Request(

    val result: String,
    @SerializedName("time_next_update_utc") val timeNextUpdateUtc: String,
    @SerializedName("time_last_update_utc") val timeLastUpdateUtc: String,
    @SerializedName("base_code") val baseCode: String,
    @SerializedName("target_code") val targetCode: String,
    @SerializedName("conversion_rates") val conversionRates: RequestDto

)