package com.example.cryptocurrencies.network

import com.example.cryptocurrencies.entity.CryptoCurrency
import com.google.gson.annotations.SerializedName

data class CurrListResponse (
    @SerializedName("data")
    val currencies: List<CryptoCurrency>
)

data class CurrencyResponse (
    @SerializedName("data")
    val currency: HashMap<String, CryptoCurrency>
)