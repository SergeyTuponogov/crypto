package com.example.cryptocurrencies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.cryptocurrencies.R
import com.example.cryptocurrencies.arch.CurrencyContract
import com.example.cryptocurrencies.entity.CryptoCurrency
import com.example.cryptocurrencies.viewmodel.CurrencyViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_currency.*
import kotlinx.android.synthetic.main.activity_currency.layout_error
import kotlin.math.round

class CurrencyActivity : AppCompatActivity(), CurrencyContract.View {

    private lateinit var viewModel : CurrencyViewModel
    private lateinit var presenter : CurrencyContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        val currency = intent.extras!!["crypto_currency"]

        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)

        val liveData = viewModel.getPresenter()

        liveData.observe(this, Observer {
            presenter = it

            presenter.attachView(this)
            presenter.fetchData(currency = currency as CryptoCurrency)
        })
    }


    override fun showCurrency(currency: CryptoCurrency) {

        text_name.text = currency.name
        text_symbol.text = currency.symbol
        text_description.text = currency.description

        // round(... * 100) / 100 - округление до двух знаков после запятой
        val price = "$${round(currency.quote!!.usd.price * 100) / 100}"
        text_price.text = price

        val percent = "${round(currency.quote.usd.percentChange * 100) / 100}%"
        text_percent.text = percent

        if (currency.quote.usd.percentChange < 0) {
            text_percent.setTextColor(ContextCompat.getColor(this, R.color.colorRed))
        }


        // toBigDecimal - чтобы нормально выводил большие числа, а не в формате 1.53749842Е12
        val volume = "$${currency.quote.usd.volume24h.toBigDecimal()}"
        text_volume.text = volume

        val marketCap = "$${currency.quote.usd.marketCap.toBigDecimal()}"
        text_market_cap.text = marketCap


        Glide.with(layout_main)
            .load(currency.logo)
            .into(image_logo)


        layout_currency.visibility = View.VISIBLE
        layout_error.visibility = View.GONE
        layout_loading.visibility = View.GONE
    }

    override fun showLoading() {
        layout_currency.visibility = View.GONE
        layout_error.visibility = View.GONE
        layout_loading.visibility = View.VISIBLE
    }

    override fun showError(msg: String?) {
        layout_currency.visibility = View.GONE
        layout_error.visibility = View.VISIBLE
        layout_loading.visibility = View.GONE

        if (msg != null) {
            Snackbar.make(layout_main, msg, Snackbar.LENGTH_LONG).show()
        }
    }
}
