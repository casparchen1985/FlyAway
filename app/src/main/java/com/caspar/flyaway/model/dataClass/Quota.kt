package com.caspar.flyaway.model.dataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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