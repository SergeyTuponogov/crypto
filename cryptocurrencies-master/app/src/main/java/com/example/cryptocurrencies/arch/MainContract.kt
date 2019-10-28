package com.example.cryptocurrencies.arch

import com.example.cryptocurrencies.entity.CryptoCurrency

// Интерфейсы для реализации MVP для экрана со списком
interface MainContract {
    interface View {
        fun showList(data: List<CryptoCurrency>)
        fun showError(msg: String?)
        fun showLoading()
    }

    interface Model {
        fun loadCryptoCurrencies()
    }

    interface Presenter {
        fun attachView(view : View)
        fun fetchData(isUpdate : Boolean = false)
        fun getCryptoCurrency(position : Int) : CryptoCurrency
    }

    interface LoadingListener {
        fun onCryptoCurrenciesLoadSuccess(data: List<CryptoCurrency>)
        fun onCryptoCurrenciesLoadFailure(t: Throwable)
    }
}