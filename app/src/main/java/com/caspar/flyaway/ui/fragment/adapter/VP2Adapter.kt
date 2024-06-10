package com.caspar.flyaway.ui.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VP2Adapter(
    context: FragmentActivity,
    private val pageList: MutableList<Fragment>
) : FragmentStateAdapter(context) {
    override fun getItemCount(): Int = pageList.size

    override fun createFragment(position: Int): Fragment = pageList[position]

}