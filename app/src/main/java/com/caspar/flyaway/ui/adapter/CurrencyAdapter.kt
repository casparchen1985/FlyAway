package com.caspar.flyaway.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caspar.flyaway.R
import com.caspar.flyaway.databinding.CellCurrencyBinding
import com.caspar.flyaway.model.dataClass.CurrencyCellInfo
import com.caspar.flyaway.ui.diffCallback.DiffCurrency

class CurrencyAdapter : ListAdapter<CurrencyCellInfo, CurrencyAdapter.CurrencyViewHolder>(DiffCurrency()) {
    private lateinit var parentContext: Context
    private var itemClickLambda: ((String) -> Unit)? = null

    class CurrencyViewHolder(private val binding: CellCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(item: CurrencyCellInfo, context: Context, clickLambda: ((String) -> Unit)?) {
            with(binding) {
                root.setOnClickListener {
                    clickLambda?.invoke(item.code)
                }

                val codeText = "[${item.code}]"
                currencyCode.text =
                    if (item.exchangeRate == 1.0f) {
                        val codeString = SpannableString(codeText)
                        codeString.setSpan(
                            ForegroundColorSpan(context.getColor(R.color.orange)),
                            0,
                            codeText.length,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                        codeString
                    } else {
                        codeText
                    }


                val nameText = item.name
                currencyName.text =
                    if (item.exchangeRate == 1.0f) {
                        val codeString = SpannableString(nameText)
                        codeString.setSpan(
                            ForegroundColorSpan(context.getColor(R.color.orange)),
                            0,
                            item.name.length,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                        codeString
                    } else {
                        item.name
                    }

                exchangeRate.text = item.exchangeRate.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        parentContext = parent.context
        return CurrencyViewHolder(CellCurrencyBinding.inflate(LayoutInflater.from(parentContext), parent, false))
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindView(getItem(position), parentContext, itemClickLambda)
    }

    fun setOnItemClickLambda(l: (String) -> Unit) {
        itemClickLambda = l
    }
}