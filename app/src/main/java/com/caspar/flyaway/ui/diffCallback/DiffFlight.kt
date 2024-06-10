package com.caspar.flyaway.ui.diffCallback

import androidx.recyclerview.widget.DiffUtil
import com.caspar.flyaway.model.dataClass.FlightInfo

class DiffFlight : DiffUtil.ItemCallback<FlightInfo>() {
    override fun areItemsTheSame(oldItem: FlightInfo, newItem: FlightInfo): Boolean {
        val isTheSameDay = oldItem.updateTime.substringBefore(" ") == newItem.updateTime.substringBefore(" ")
        val isTheSameFlight = oldItem.airlineId + oldItem.flightNumber == newItem.airlineId + newItem.flightNumber
        return isTheSameDay && isTheSameFlight
    }

    override fun areContentsTheSame(oldItem: FlightInfo, newItem: FlightInfo): Boolean {
        return oldItem == newItem
    }
}