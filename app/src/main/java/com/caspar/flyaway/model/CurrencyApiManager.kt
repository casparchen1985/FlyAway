package com.caspar.flyaway.model

import android.util.Log
import com.caspar.flyaway.R
import com.caspar.flyaway.di.FlyAwayApplication
import com.caspar.flyaway.model.dataClass.CurrencyResult
import com.caspar.flyaway.model.dataClass.ExchangeResult
import com.caspar.flyaway.model.dataClass.QuotaResult
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.Collections
import java.util.concurrent.TimeUnit

object CurrencyApiManager {
    private val logger = HttpLoggingInterceptor { msg ->
        Log.i("HttpLog", msg)
    }.apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private var gsonConvert: GsonConverterFactory? = null
        get() {
            if (field == null) {
                field = GsonConverterFactory.create(
                    GsonBuilder().setStrictness(Strictness.LENIENT).disableHtmlEscaping().create()
                )
            }
            return field
        }

    private val networkInterceptor = Interceptor {
        val request = it
            .request()
            .newBuilder()
            .addHeader("apikey", FlyAwayApplication.appContext.getString(R.string.currency_key))
            .build()
        it.proceed(request)
    }

    private var httpClient: OkHttpClient? = null
        get() {
            if (field == null) {
                field = OkHttpClient()
                    .newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .addInterceptor(logger)
                    .addNetworkInterceptor(networkInterceptor)
                    //.addInterceptor(exceptionInterceptor)
                    .build()
            }
            return field
        }

    var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                field = Retrofit
                    .Builder()
                    .baseUrl(FlyAwayApplication.appContext.getString(R.string.currency_api_url))
                    .client(httpClient!!)
                    .addConverterFactory(gsonConvert!!)
                    .build()
            }
            return field
        }
}

interface Currency {
    @GET("currencies/?")
    suspend fun fetchCurrencies(@QueryMap string: Map<String, String>): Response<CurrencyResult>

    @GET("currencies")
    suspend fun fetchCurrencies(): Response<CurrencyResult>

    @GET("status")
    suspend fun fetchApiStatus(@QueryMap string: Map<String, String>): Response<QuotaResult>

    @GET("latest/?")
    suspend fun fetchExchangeRates(@QueryMap string: Map<String, String>): Response<ExchangeResult>

    @GET("latest")
    suspend fun fetchExchangeRates(): Response<ExchangeResult>
}
