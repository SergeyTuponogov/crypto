package com.example.cryptocurrencies.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencies.R
import com.example.cryptocurrencies.adapter.CurrenciesAdapter
import com.example.cryptocurrencies.arch.MainContract
import com.example.cryptocurrencies.entity.CryptoCurrency
import com.example.cryptocurrencies.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var viewModel : MainViewModel
    private lateinit var presenter: MainContract.Presenter

    private lateinit var adapter : CurrenciesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val liveData = viewModel.getPresenter()

        liveData.observe(this, Observer {
            presenter = it

            Log.d("DS", "in observer")

            presenter.attachView(this)
            presenter.fetchData()
        })
    }

    override fun onStart() {
        super.onStart()

        layout_swipeRefresh.setOnRefreshListener {
            presenter.fetchData(true)
        }
    }

    override fun showList(data: List<CryptoCurrency>) {
        layout_swipeRefresh.isRefreshing = false

        if (!::adapter.isInitialized) {

            val onItemClickListener = View.OnClickListener {
                val position = rv_currencies.getChildAdapterPosition(it)

                val intent = Intent(this, CurrencyActivity::class.java)
                intent.putExtra("crypto_currency", presenter.getCryptoCurrency(position))

                startActivity(intent)
            }

            adapter = CurrenciesAdapter(onItemClickListener)

            rv_currencies.adapter = adapter
            rv_currencies.layoutManager = LinearLayoutManager(this)
        }

        adapter.setCurrencies(data)

        rv_currencies.visibility = View.VISIBLE
        layout_error.visibility = View.GONE
        layout_loading.visibility = View.GONE
    }

    override fun showError(msg: String?) {
        layout_swipeRefresh.isRefreshing = false

        rv_currencies.visibility = View.GONE
        layout_error.visibility = View.VISIBLE
        layout_loading.visibility = View.GONE

        if (msg != null) {
            Snackbar.make(layout_main, msg, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun showLoading() {
        layout_swipeRefresh.isRefreshing = false

        rv_currencies.visibility = View.GONE
        layout_error.visibility = View.GONE
        layout_loading.visibility = View.VISIBLE
    }

}
