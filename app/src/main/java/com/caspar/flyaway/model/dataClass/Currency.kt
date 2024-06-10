package com.caspar.flyaway.model.dataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Currency
@Parcelize
data class CurrencyResult(
    @SerializedName("data")
    val data: Map<String, CurrencyInfo> = mapOf()
) : Parcelable

@Parcelize
data class CurrencyInfo(
    @SerializedName("symbol")
    val symbol: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("symbol_native")
    val symbolNative: String = "",
    @SerializedName("decimal_digits")
    val decimalDigits: Int = 0,
    @SerializedName("rounding")
    val rounding: Int = 0,
    @SerializedName("code")
    val code: String = "",
    @SerializedName("name_plural")
    val namePlural: String = "",
) : Parcelable

// Exchange Rate
@Parcelize
data class ExchangeResult(
    @SerializedName("data")
    val data: Map<String, Float> = mapOf()
) : Parcelable


// Api Status
@Parcelize
data class QuotaResult(
    @SerializedName("quotas")
    val quotas: Map<String, QuotaDetail> = mapOf()
) : Parcelable

@Parcelize
data class QuotaDetail(
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("used")
    val used: Int = 0,
    @SerializedName("remaining")
    val remaining: Int = 0,
) : Parcelable

// RecycleView Using
@Parcelize
data class CurrencyCellInfo(
    var code: String = "",
    var name: String = "",
    var exchangeRate: Float = 0f,
) : Parcelable