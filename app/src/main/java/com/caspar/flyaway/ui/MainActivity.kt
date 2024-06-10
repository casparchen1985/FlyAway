package com.caspar.flyaway.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.caspar.flyaway.R
import com.caspar.flyaway.databinding.ActivityMainBinding
import com.caspar.flyaway.ui.fragment.adapter.VP2Adapter
import com.caspar.flyaway.ui.fragment.currency.CurrencyFragment
import com.caspar.flyaway.ui.fragment.flight.FlightFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNaviView: BottomNavigationView
    private lateinit var vp2: ViewPager2
    private val pagesForVP2 = mutableListOf(
        FlightFragment(),
        CurrencyFragment(),
    )
    private val defaultFragment = Pair(0, R.id.bottomNaviFlight)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // vp2 config
        vp2 = binding.vp2View
        vp2.apply {
            isUserInputEnabled = false
            adapter = VP2Adapter(this@MainActivity, pagesForVP2)
            setCurrentItem(defaultFragment.first, false)
        }

        // bottomNaviView config
        bottomNaviView = binding.bottomNaviView
        bottomNaviView.apply {
            selectedItemId = defaultFragment.second
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.bottomNaviFlight -> vp2.setCurrentItem(0, false)
                    R.id.bottomNaviCurrency -> vp2.setCurrentItem(1, false)
                }
                true
            }
            //do nothing
            setOnItemReselectedListener { }
        }
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        vp2.adapter = null
    }
}