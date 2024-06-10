package com.caspar.flyaway.ui.fragment.flight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caspar.flyaway.R
import com.caspar.flyaway.databinding.FragmentFlightBinding
import com.caspar.flyaway.model.enumClass.FlightType
import com.caspar.flyaway.ui.adapter.FlightAdapter
import com.caspar.flyaway.ui.decoration.FlightDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightFragment : Fragment() {
    private var _binding: FragmentFlightBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FlightViewModel by viewModels()
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
        flightRecyclerView = binding.flightRecyclerView
        flightRecyclerView.apply {
            adapter = flightAdapter
            layoutManager = flightLayoutManager
            addItemDecoration(FlightDecoration())
            itemAnimator = null
        }

        with(binding) {
            airportSpinner.apply {
                adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    viewModel.allAirports
                )
                viewModel.allAirports.forEachIndexed { index, string ->
                    if (string.contains(viewModel.defaultAirport.name)) {
                        setSelection(index, false)
                    }
                }
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        viewModel.fetchFlightInfo(p2)
                    }

                    // do nothing
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
            }

            departureButton.setOnClickListener {
                showProgressBar(true)
                showIndicator(it)
                viewModel.fetchFlightInfo(FlightType.Departure)
            }

            arrivalButton.setOnClickListener {
                showProgressBar(true)
                showIndicator(it)
                viewModel.fetchFlightInfo(FlightType.Arrival)
            }
        }

        viewModel.flightInfoLiveData.observe(viewLifecycleOwner) {
            Log.d("cas", "${it.size} FlightInfo records")
            flightAdapter.submitList(it)
            showEmptyMessage(it.isEmpty())
            showProgressBar(false)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFlightInfo()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopAutoRefresh()
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

    private fun showEmptyMessage(setting: Boolean) {
        with(binding) {
            val targetEmptyMessageStatus = if (setting) View.VISIBLE else View.GONE
            if (emptyMessage.visibility == targetEmptyMessageStatus) return

            emptyMessage.visibility = if (setting) View.VISIBLE else View.GONE
            flightRecyclerView.visibility = if (setting) View.GONE else View.VISIBLE
        }
    }
}