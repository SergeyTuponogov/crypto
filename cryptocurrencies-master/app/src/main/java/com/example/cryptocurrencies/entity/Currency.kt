package com.example.cryptocurrencies.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Инфа о валюте
data class Currency(
    @SerializedName("price")
    val price : Double,

    @SerializedName("volume_24h")
    val volume24h : Double,

    @SerializedName("percent_change_24h")
    val percentChange : Double,

    @SerializedName("market_cap")
    val marketCap: Double
) : Serializable