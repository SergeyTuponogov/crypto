package com.example.cryptocurrencies.model

import com.example.cryptocurrencies.arch.CurrencyContract
import com.example.cryptocurrencies.network.CurrencyResponse
import com.example.cryptocurrencies.network.RequestService
import com.example.cryptocurrencies.presenter.CurrencyPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyModel(private val presenter: CurrencyPresenter) : CurrencyContract.Model {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pro-api.coinmarketcap.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(RequestService::class.java)

    override fun loadCurrencyInfo(id: Int) {
        val call = service.getCryptoCurrencyInfo(id = id)

        call.enqueue(object : Callback<CurrencyResponse> {
            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                presenter.onLoadInfoFailure(t)
            }

            override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null) {
                        presenter.onLoadInfoSuccess(body.currency.values.first())
                    } else {
                        presenter.onLoadInfoFailure(Throwable("Response body was null"))
                    }
                } else {
                    presenter.onLoadInfoFailure(Throwable("Response is failure. Check network connection and try again"))
                }
            }

        })
    }
}