package com.caspar.flyaway.ui.fragment.flight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caspar.flyaway.R
import com.caspar.flyaway.databinding.FragmentFlightBinding
import com.caspar.flyaway.model.enumClass.Airport
import com.caspar.flyaway.model.enumClass.FlightType
import com.caspar.flyaway.ui.adapter.FlightAdapter
import com.caspar.flyaway.ui.decoration.FlightDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightFragment : Fragment() {
    private var _binding: FragmentFlightBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FlightViewModel by viewModels()
    private val defaultType = FlightType.Departure
    private val defaultAirport = Airport.TPE
    private lateinit var flightRecyclerView: RecyclerView
    private lateinit var flightAdapter: FlightAdapter
    private lateinit var flightLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flightLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)
        flightLayoutManager.stackFromEnd = true

        flightAdapter = FlightAdapter()
        flightAdapter.setOnListChangedLambda {
            showProgressBar(false)
        }
        flightRecyclerView = binding.displayView
        flightRecyclerView.apply {
            adapter = flightAdapter
            layoutManager = flightLayoutManager
            addItemDecoration(FlightDecoration())
            itemAnimator = null
        }

        with(binding) {
            departureButton.setOnClickListener {
                showProgressBar(true)
                showIndicator(it)
                viewModel.fetchFlightInfo(FlightType.Departure, Airport.TPE)
            }

            arrivalButton.setOnClickListener {
                showProgressBar(true)
                showIndicator(it)
                viewModel.fetchFlightInfo(FlightType.Arrival, Airport.TPE)
            }
        }

        viewModel.flightInfoLiveData.observe(viewLifecycleOwner) {
            Log.d("cas", "flightInfoLiveData: ${it.size} records")
            flightAdapter.submitList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFlightInfo(
            viewModel.current?.first ?: defaultType,
            viewModel.current?.second ?: defaultAirport
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgressBar(setting: Boolean) {
        binding.progressBarLayout.visibility = if (setting) View.VISIBLE else View.GONE
    }

    private fun showIndicator(view: View) {
        val parentLayout: ConstraintLayout = binding.root
        val newSet = ConstraintSet()
        newSet.clone(parentLayout)
        newSet.connect(
            R.id.select_indicator,
            ConstraintSet.START,
            if (view.tag == requireContext().getString(R.string.tag_departure)) R.id.departure_button else R.id.arrival_button,
            ConstraintSet.START
        )
        newSet.connect(
            R.id.select_indicator,
            ConstraintSet.END,
            if (view.tag == requireContext().getString(R.string.tag_departure)) R.id.departure_button else R.id.arrival_button,
            ConstraintSet.END
        )
        newSet.applyTo(parentLayout)
    }
}