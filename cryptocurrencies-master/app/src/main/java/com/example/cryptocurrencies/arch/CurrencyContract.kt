package com.example.cryptocurrencies.arch

import com.example.cryptocurrencies.entity.CryptoCurrency

// Интерфейсы для реализации MVP для экрана с инфой о валюте
interface CurrencyContract {

    interface View {
        fun showCurrency(currency : CryptoCurrency)
        fun showLoading()
        fun showError(msg : String?)
    }

    interface Presenter {
        fun attachView(view : View)
        fun fetchData(isUpdate : Boolean = false, currency: CryptoCurrency)
    }

    interface Model {
        fun loadCurrencyInfo(id : Int)
    }

    interface LoadingListener {
        fun onLoadInfoSuccess(data: CryptoCurrency)
        fun onLoadInfoFailure(t: Throwable)
    }
}