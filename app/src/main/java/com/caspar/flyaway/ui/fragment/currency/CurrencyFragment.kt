package com.caspar.flyaway.ui.fragment.currency

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caspar.flyaway.databinding.FragmentCurrencyBinding
import com.caspar.flyaway.ui.adapter.CurrencyAdapter
import com.caspar.flyaway.ui.decoration.FlightDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyFragment : Fragment() {
    private val viewModel: CurrencyViewModel by viewModels()
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!
    private lateinit var currencyRecyclerView: RecyclerView
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var currencyLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currencyLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        currencyAdapter = CurrencyAdapter()
        currencyAdapter.setOnItemClickLambda {
            viewModel.fetchData(it)
        }

        currencyRecyclerView = binding.currencyRecyclerView
        currencyRecyclerView.apply {
            adapter = currencyAdapter
            layoutManager = currencyLayoutManager
            addItemDecoration(FlightDecoration())
            itemAnimator = null
        }

        viewModel.currencyLiveData.observe(viewLifecycleOwner) {
            Log.d("cas", "${it.size} CurrencyCellInfo records")
            currencyAdapter.submitList(it)
            showEmptyMessage(it.isEmpty())
            showProgressBar(false)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
        showProgressBar(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgressBar(setting: Boolean) {
        binding.progressBarLayout.visibility = if (setting) View.VISIBLE else View.GONE
    }

    private fun showEmptyMessage(setting: Boolean) {
        with(binding) {
            val targetEmptyMessageStatus = if (setting) View.VISIBLE else View.GONE
            if (emptyMessage.visibility == targetEmptyMessageStatus) return

            emptyMessage.visibility = if (setting) View.VISIBLE else View.GONE
            currencyRecyclerView.visibility = if (setting) View.GONE else View.VISIBLE
        }
    }
}