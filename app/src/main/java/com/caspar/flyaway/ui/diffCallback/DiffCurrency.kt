package com.caspar.flyaway.ui.diffCallback

import androidx.recyclerview.widget.DiffUtil
import com.caspar.flyaway.model.dataClass.CurrencyCellInfo

class DiffCurrency : DiffUtil.ItemCallback<CurrencyCellInfo>() {
    override fun areItemsTheSame(oldItem: CurrencyCellInfo, newItem: CurrencyCellInfo): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: CurrencyCellInfo, newItem: CurrencyCellInfo): Boolean {
        return oldItem == newItem
    }
}