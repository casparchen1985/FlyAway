package com.caspar.flyaway.model

import android.util.Log
import com.caspar.flyaway.model.dataClass.FlightInfo
import javax.inject.Inject
import javax.inject.Singleton

interface FlightRepository {
    suspend fun fetchFlightInfo(type: String, airport: String): List<FlightInfo>?
}

@Singleton
class FlightRepositoryImpl @Inject constructor(
    private val apiService: Flight
) : FlightRepository {

    override suspend fun fetchFlightInfo(type: String, airport: String): List<FlightInfo>? {
        var response: retrofit2.Response<List<FlightInfo>>? = null

        try {
            response = apiService.fetchFlightInfo(type, airport)
        } catch (e: Exception) {
            Log.d("FlightApiException", e.message.toString())
        }

        return when {
            response?.body() == null -> null
            response.errorBody()?.string()?.isNotEmpty() == true -> {
                Log.d("cas", "flight error message:\n${response.errorBody()!!.string()}")
                null
            }

            else -> response.body()
        }
    }

}