package com.example.cryptocurrencies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencies.arch.MainContract
import com.example.cryptocurrencies.presenter.MainPresenter

// ViewModel - для того, чтобы при смене конфигураций (поворот экрана etc) ничего не ломалось
// и не грузилось заново
// Подробнее: https://startandroid.ru/ru/courses/architecture-components/27-course/architecture-components/527-urok-4-viewmodel.html
class MainViewModel : ViewModel() {

    private lateinit var presenter : MutableLiveData<MainContract.Presenter>


    fun getPresenter() : LiveData<MainContract.Presenter> {
        if (!::presenter.isInitialized) {
            presenter = MutableLiveData()
            presenter.postValue(MainPresenter())
        }

        return presenter
    }
}