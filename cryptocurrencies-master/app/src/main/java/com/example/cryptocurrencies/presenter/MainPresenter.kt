package com.example.cryptocurrencies.presenter

import com.example.cryptocurrencies.arch.MainContract
import com.example.cryptocurrencies.entity.CryptoCurrency
import com.example.cryptocurrencies.model.MainModel

class MainPresenter : MainContract.Presenter, MainContract.LoadingListener {

    private lateinit var view : MainContract.View
    private val model = MainModel(this)

    private val cryptoCurrencies = ArrayList<CryptoCurrency>()


    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun fetchData(isUpdate: Boolean) {
        if (isUpdate || cryptoCurrencies.isEmpty()) {
            view.showLoading()
            model.loadCryptoCurrencies()
        } else {
            view.showList(cryptoCurrencies)
        }
    }

    override fun onCryptoCurrenciesLoadSuccess(data: List<CryptoCurrency>) {
        cryptoCurrencies.clear()
        cryptoCurrencies.addAll(data)

        view.showList(cryptoCurrencies)
    }

    override fun onCryptoCurrenciesLoadFailure(t: Throwable) {
        view.showError(t.message)
    }

    override fun getCryptoCurrency(position: Int): CryptoCurrency {
        return cryptoCurrencies[position]
    }
}