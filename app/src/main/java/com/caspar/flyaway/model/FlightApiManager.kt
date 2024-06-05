package com.caspar.flyaway.model

import android.util.Log
import com.caspar.flyaway.R
import com.caspar.flyaway.di.FlyAwayApplication
import com.caspar.flyaway.model.dataClass.FlightInfo
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
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

object FlightApiManager {
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
                    .baseUrl(FlyAwayApplication.appContext.getString(R.string.fly_api_url))
                    .client(httpClient!!)
                    .addConverterFactory(gsonConvert!!)
                    .build()
            }
            return field
        }
}

interface Flight {
    @GET(".")
    suspend fun fetchFlight(@QueryMap string: Map<String, String>): Response<List<FlightInfo>>
}