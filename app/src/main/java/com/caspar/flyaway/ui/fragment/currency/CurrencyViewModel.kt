package com.caspar.flyaway.ui.fragment.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caspar.flyaway.model.CurrencyRepository
import com.caspar.flyaway.model.dataClass.CurrencyCellInfo
import com.caspar.flyaway.model.dataClass.CurrencyInfo
import com.caspar.flyaway.ui.tool.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepos: CurrencyRepository
) : ViewModel() {
    val currencyLiveData = SingleLiveData<List<CurrencyCellInfo>>()
    private var currencies: List<CurrencyInfo> = emptyList()
    private var exchangeRates: List<Map.Entry<String, Float>>? = null
    private var codeCache: String? = null

    fun fetchData(code: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val jobs = mutableListOf(
                launch { fetchCurrency() },
                launch { fetchExchangeRate(code ?: codeCache) }
            )
            jobs.joinAll()
            genCurrencyCellInfo()
        }
    }

    private suspend fun fetchCurrency() {
        currencyRepos.fetchCurrency().let { result ->
            currencies =
                result?.data?.entries?.sortedBy { it.key }?.map { it.value } ?: emptyList()
        }
    }

    private suspend fun fetchExchangeRate(code: String?) {
        currencyRepos.fetchExchangeRate(code).let { result ->
            exchangeRates =
                result?.data?.entries?.sortedBy { it.key }
            codeCache = code
        }
    }

    private fun genCurrencyCellInfo() {
        if (exchangeRates == null || currencies.isEmpty()) {
            currencyLiveData.postValue(emptyList())
            return
        }

        // 紀錄每個貨幣的 Code 及 其座標
        val codeIndexMap = mutableMapOf<String, Int>()
        currencies.forEachIndexed { index, currencyInfo ->
            codeIndexMap[currencyInfo.code] = index
        }

        val resultList = mutableListOf<CurrencyCellInfo>()

        // 透過匯率資料集的code 去查找座標紀錄中的資料, 並取出其完整名稱
        exchangeRates!!.forEach { rateEntry ->
            val targetIndex = codeIndexMap[rateEntry.key] ?: return@forEach
            resultList.add(CurrencyCellInfo(rateEntry.key, currencies[targetIndex].name, rateEntry.value))
        }

        currencyLiveData.postValue(resultList)
    }
}