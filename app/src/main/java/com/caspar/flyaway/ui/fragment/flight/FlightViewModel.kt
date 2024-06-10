package com.caspar.flyaway.ui.fragment.flight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caspar.flyaway.model.FlightRepository
import com.caspar.flyaway.model.dataClass.FlightInfo
import com.caspar.flyaway.model.enumClass.Airport
import com.caspar.flyaway.model.enumClass.FlightType
import com.caspar.flyaway.ui.tool.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val flightRepos: FlightRepository
) : ViewModel() {
    val flightInfoLiveData = SingleLiveData<List<FlightInfo>>()
    private val defaultType = FlightType.Departure
    val defaultAirport = Airport.TPE
    private var current: Pair<FlightType, Airport>? = Pair(defaultType, defaultAirport)
    val allAirports = defaultAirport.getAllDisplayName()
    private val autoRefreshPeriod = 180 * 1000L
    private var autoRefreshJob: Job? = null

    fun fetchFlightInfo() {
        val newPair = Pair(
            current?.first ?: defaultType,
            current?.second ?: defaultAirport
        )
        getAndPostFlightInfo(newPair)
    }

    fun fetchFlightInfo(index: Int) {
        val newPair = Pair(
            current?.first ?: defaultType,
            defaultAirport.getItemByPosition(index)
        )
        getAndPostFlightInfo(newPair)
    }

    fun fetchFlightInfo(type: FlightType) {
        val newPair = Pair(
            type,
            current?.second ?: defaultAirport,
        )
        getAndPostFlightInfo(newPair)
    }

    private fun getAndPostFlightInfo(newPair: Pair<FlightType, Airport>) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = flightRepos.fetchFlightInfo(
                newPair.first.toAPICode(),
                newPair.second.toAPICode()
            )

            flightInfoLiveData.postValue(
                if (data != null) {
                    current = newPair
                    data
                } else emptyList()
            )

            // 用 coroutine + delay 達到計時器的功能
            autoRefreshJob?.cancel()
            autoRefreshJob = launch(Dispatchers.IO) {
                delay(autoRefreshPeriod)
                fetchFlightInfo()
            }
        }
    }

    fun stopAutoRefresh() {
        autoRefreshJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        autoRefreshJob?.cancel()
    }
}