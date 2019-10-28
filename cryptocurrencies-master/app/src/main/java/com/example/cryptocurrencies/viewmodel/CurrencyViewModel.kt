package com.example.cryptocurrencies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencies.arch.CurrencyContract
import com.example.cryptocurrencies.presenter.CurrencyPresenter

class CurrencyViewModel : ViewModel() {

    private lateinit var presenter : MutableLiveData<CurrencyContract.Presenter>



    fun getPresenter() : LiveData<CurrencyContract.Presenter> {
        if (!::presenter.isInitialized) {
            presenter = MutableLiveData()
            presenter.postValue(CurrencyPresenter())
        }

        return presenter
    }
}