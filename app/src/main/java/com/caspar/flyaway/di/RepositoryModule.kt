package com.caspar.flyaway.di

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
}