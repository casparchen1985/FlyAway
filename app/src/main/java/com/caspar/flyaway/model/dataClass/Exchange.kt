package com.caspar.flyaway.model.dataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExchangeResult(
    @SerializedName("data")
    val data: Map<String, Float> = mapOf()
) : Parcelable