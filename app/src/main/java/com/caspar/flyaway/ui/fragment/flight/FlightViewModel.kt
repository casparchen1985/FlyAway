package com.caspar.flyaway.ui.fragment.flight

import androidx.lifecycle.ViewModel
import com.caspar.flyaway.model.FlightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val flightRepos: FlightRepository
) : ViewModel() {

}