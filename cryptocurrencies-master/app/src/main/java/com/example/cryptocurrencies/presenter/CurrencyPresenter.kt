package com.example.cryptocurrencies.presenter

import android.util.Log
import com.example.cryptocurrencies.arch.CurrencyContract
import com.example.cryptocurrencies.entity.CryptoCurrency
import com.example.cryptocurrencies.model.CurrencyModel

class CurrencyPresenter : CurrencyContract.Presenter, CurrencyContract.LoadingListener {

    private lateinit var view : CurrencyContract.View
    private val model = CurrencyModel(this)

    private lateinit var currentCurrency : CryptoCurrency


    override fun attachView(view: CurrencyContract.View) {
        this.view = view
    }

    override fun fetchData(isUpdate: Boolean, currency: CryptoCurrency) {
        if (isUpdate || !::currentCurrency.isInitialized) {
            view.showLoading()
            currentCurrency = currency
            model.loadCurrencyInfo(currency.id)
        } else {
            view.showCurrency(currentCurrency)
        }
    }

    override fun onLoadInfoSuccess(data: CryptoCurrency) {
        currentCurrency.description = data.description
        currentCurrency.logo = data.logo

        Log.d("das", "success! ${currentCurrency.description}")

        view.showCurrency(currentCurrency)
    }

    override fun onLoadInfoFailure(t: Throwable) {
        view.showError(t.message)
    }
}