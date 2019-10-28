package com.example.cryptocurrencies.model

import com.example.cryptocurrencies.arch.MainContract
import com.example.cryptocurrencies.entity.CryptoCurrency
import com.example.cryptocurrencies.network.CurrListResponse
import com.example.cryptocurrencies.network.RequestService
import com.example.cryptocurrencies.presenter.MainPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainModel(val presenter: MainPresenter) : MainContract.Model {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pro-api.coinmarketcap.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(RequestService::class.java)



    override fun loadCryptoCurrencies(){
        val call = service.getCryptoCurrencies()

        call.enqueue(object : Callback<CurrListResponse> {
            override fun onFailure(call: Call<CurrListResponse>, t: Throwable) {
                presenter.onCryptoCurrenciesLoadFailure(t)
            }

            override fun onResponse(call: Call<CurrListResponse>, response: Response<CurrListResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null) {
                        presenter.onCryptoCurrenciesLoadSuccess(body.currencies)
                    } else {
                        presenter.onCryptoCurrenciesLoadFailure(Throwable("Response body is null"))
                    }
                } else {
                    presenter.onCryptoCurrenciesLoadFailure(Throwable("Response is failure. Check network connection and try again"))
                }
            }

        })
    }
}