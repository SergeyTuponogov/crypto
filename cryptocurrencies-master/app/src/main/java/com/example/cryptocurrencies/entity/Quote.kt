package com.example.cryptocurrencies.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Класс, содержащий инфу о валюте
data class Quote(
    @SerializedName("USD")
    val usd : Currency
) : Serializable