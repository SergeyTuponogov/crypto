package com.example.cryptocurrencies.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Криптовалюта
data class CryptoCurrency(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("slug")
    val slug: String,

    @SerializedName("quote")
    val quote: Quote?,

    @SerializedName("description")
    var description: String?,

    @SerializedName("logo")
    var logo: String?
) : Serializable