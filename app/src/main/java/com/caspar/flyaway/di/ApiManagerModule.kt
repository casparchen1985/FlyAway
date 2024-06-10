package com.caspar.flyaway.di

import com.caspar.flyaway.model.Currency
import com.caspar.flyaway.model.CurrencyApiManager
import com.caspar.flyaway.model.Flight
import com.caspar.flyaway.model.FlightApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiManagerModule {

    @Singleton
    @Provides
    fun providesFlightService(): Flight {
        return FlightApiManager.retrofit!!.create<Flight>()
    }

    @Singleton
    @Provides
    fun providesCurrencyService(): Currency {
        return CurrencyApiManager.retrofit!!.create<Currency>()
    }
}