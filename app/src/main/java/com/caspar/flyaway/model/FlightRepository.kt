package com.caspar.flyaway.model

import javax.inject.Inject
import javax.inject.Singleton

interface FlightRepository {

}

@Singleton
class FlightRepositoryImpl @Inject constructor(
    private val apiService: Flight
) : FlightRepository {

}