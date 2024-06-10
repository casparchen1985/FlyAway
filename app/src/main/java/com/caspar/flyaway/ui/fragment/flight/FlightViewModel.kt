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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val flightRepos: FlightRepository
) : ViewModel() {
    val flightInfoLiveData = SingleLiveData<List<FlightInfo>>()
    private var _current: Pair<FlightType, Airport>? = null
    val current get() = _current

    fun fetchFlightInfo(type: FlightType, airport: Airport) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = flightRepos.fetchFlightInfo(type.toAPICode(), airport.toAPICode())

            flightInfoLiveData.postValue(
                if (data?.isNotEmpty() == true) {
                    _current = Pair(type, airport)
                    data
                } else {
                    if (data == null) _current = null
                    emptyList()
                }
            )
        }
    }
}