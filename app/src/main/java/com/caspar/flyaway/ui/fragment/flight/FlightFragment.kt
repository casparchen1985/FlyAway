package com.caspar.flyaway.ui.fragment.flight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.caspar.flyaway.databinding.FragmentFlightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightFragment : Fragment() {
    private var _binding: FragmentFlightBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FlightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

}