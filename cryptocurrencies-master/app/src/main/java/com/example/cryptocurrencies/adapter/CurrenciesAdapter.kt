package com.example.cryptocurrencies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencies.R
import com.example.cryptocurrencies.entity.CryptoCurrency
import kotlin.math.round


class CurrenciesAdapter(private val onItemClickListener: View.OnClickListener) : RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {

    private var data = ArrayList<CryptoCurrency>()

    fun setCurrencies(data: List<CryptoCurrency>) {
        this.data.clear()
        this.data.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_currency, parent, false)

        view.setOnClickListener(onItemClickListener)

        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTextView : TextView
        private val symbolTextView : TextView
        private val priceTextView : TextView
        private val percentTextView : TextView
        private val volumeTextView : TextView
        private val marketCapTextView : TextView


        init {
            super.itemView

            nameTextView = view.findViewById(R.id.text_name)
            symbolTextView = view.findViewById(R.id.text_symbol)
            priceTextView = view.findViewById(R.id.text_price)
            percentTextView = view.findViewById(R.id.text_percent)
            volumeTextView = view.findViewById(R.id.text_volume)
            marketCapTextView = view.findViewById(R.id.text_market_cap)
        }


        fun bind(item : CryptoCurrency) {
            nameTextView.text = item.name
            symbolTextView.text = item.symbol

            // round(... * 100) / 100 - округление до двух знаков после запятой
            val price = "$${round(item.quote!!.usd.price * 100) / 100}"
            priceTextView.text = price

            val percent = "${round(item.quote.usd.percentChange * 100) / 100}%"
            percentTextView.text = percent

            if (item.quote.usd.percentChange < 0) {
                percentTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorRed))
            }


            // toBigDecimal - чтобы нормально выводил большие числа, а не в формате 1.53749842Е12
            val volume = "$${item.quote.usd.volume24h.toBigDecimal()}"
            volumeTextView.text = volume

            val marketCap = "$${item.quote.usd.marketCap.toBigDecimal()}"
            marketCapTextView.text = marketCap
        }
    }
}