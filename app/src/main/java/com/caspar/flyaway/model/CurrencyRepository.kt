package com.caspar.flyaway.model

import android.util.Log
import com.caspar.flyaway.model.dataClass.CurrencyResult
import com.caspar.flyaway.model.dataClass.ExchangeResult
import javax.inject.Inject

interface CurrencyRepository {
    suspend fun fetchCurrency(): CurrencyResult?
    suspend fun fetchExchangeRate(code: String? = null): ExchangeResult?
}

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: Currency
) : CurrencyRepository {
    override suspend fun fetchCurrency(): CurrencyResult? {
        var response: retrofit2.Response<CurrencyResult>? = null
        try {
            response = apiService.fetchCurrencies()
        } catch (e: Exception) {
            Log.d("cas", "Currency api exception:\n${e.message}")
        }

        return when {
            response?.body() == null -> null
            response.errorBody()?.string()?.isNotEmpty() == true -> {
                Log.d("cas", "Currency api error message:\n${response.errorBody()!!.string()}")
                null
            }

            else -> response.body()
        }
    }

    override suspend fun fetchExchangeRate(code: String?): ExchangeResult? {
        var response: retrofit2.Response<ExchangeResult>? = null
        try {
            response =
                if (code != null) {
                    val parameter = mapOf("base_currency" to code)
                    apiService.fetchExchangeRates(parameter)
                } else {
                    apiService.fetchExchangeRates()
                }
        } catch (e: Exception) {
            Log.d("cas", "Exchange api exception:\n${e.message}")
        }

        return when {
            response?.body() == null -> null
            response.errorBody()?.string()?.isNotEmpty() == true -> {
                Log.d("cas", "Exchange api error message:\n${response.errorBody()!!.string()}")
                null
            }

            else -> response.body()
        }
    }

}