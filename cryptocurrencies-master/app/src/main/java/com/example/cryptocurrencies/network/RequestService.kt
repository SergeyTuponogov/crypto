package com.example.cryptocurrencies.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RequestService {

    private val apiKey : String
        get() = "bae5e149-a153-47e1-81d7-6c64adbfa82e"


    /**
     * Притянуть топ 100 криптовалют
     * В заголовок запроса указываем API ключ, иначе выдаст ошибку
     */
    @GET("cryptocurrency/listings/latest?start=1&limit=50&convert=USD")
    fun getCryptoCurrencies(@Header("X-CMC_PRO_API_KEY") apiKey : String = this.apiKey) : Call<CurrListResponse>


    /**
     * Притянуть доп информацию для криптовалюты с указанным id
     */
    @GET("cryptocurrency/info")
    fun getCryptoCurrencyInfo(@Header("X-CMC_PRO_API_KEY") apiKey : String = this.apiKey, @Query("id") id: Int) : Call<CurrencyResponse>
}