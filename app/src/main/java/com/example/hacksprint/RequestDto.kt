package com.example.hacksprint

import kotlinx.serialization.Contextual


@kotlinx.serialization.Serializable

data class Request (
    val result: String,
    val time_last_update_utc: String,
    val time_next_update_utc: String,
    val base_code: String,
    @Contextual
    val rates: Currency
)