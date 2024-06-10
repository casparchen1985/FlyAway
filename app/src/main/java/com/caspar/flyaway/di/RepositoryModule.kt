package com.caspar.flyaway.di

import com.caspar.flyaway.model.Currency
import com.caspar.flyaway.model.CurrencyRepository
import com.caspar.flyaway.model.CurrencyRepositoryImpl
import com.caspar.flyaway.model.Flight
import com.caspar.flyaway.model.FlightRepository
import com.caspar.flyaway.model.FlightRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideFlightRepository(apiService: Flight): FlightRepository {
        return FlightRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(apiService: Currency): CurrencyRepository {
        return CurrencyRepositoryImpl(apiService)
    }
}