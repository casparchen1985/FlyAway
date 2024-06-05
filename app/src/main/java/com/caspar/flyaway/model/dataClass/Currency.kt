package com.caspar.flyaway.model.dataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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