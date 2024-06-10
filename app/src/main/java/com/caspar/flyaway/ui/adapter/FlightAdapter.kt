package com.caspar.flyaway.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caspar.flyaway.R
import com.caspar.flyaway.databinding.CellFlightBinding
import com.caspar.flyaway.model.dataClass.FlightInfo
import com.caspar.flyaway.ui.diffCallback.DiffFlight

class FlightAdapter : ListAdapter<FlightInfo, FlightAdapter.FlightViewHolder>(DiffFlight()) {
    private lateinit var parentContext: Context
    private var onListChangedLambda: (() -> Unit)? = null

    class FlightViewHolder(private val binding: CellFlightBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(info: FlightInfo, context: Context) {
            with(binding) cell@ {
                airline.text = info.airline
                airlineId.text = info.airlineId
                flightNumber.text = info.flightNumber
                terminal.text = info.terminal + " 航廈"
                gate.text = (info.gate ?: "N/A") + " 登機門"
                departureAirport.text = info.departureAirport + "(" + info.departureAirportId + ")"
                arrivalAirport.text = info.arrivalAirport + "(" + info.arrivalAirportId + ")"
                scheduleTime.text = info.scheduleTime
                estimateTime.text = info.estimatedTime
                actualTime.text = info.actualTime
                with(info.remark) {
                    val fontColor =
                        if (this.contains("DEPARTED") || this.contains("ARRIVED")) {
                            R.color.gray_dark
                        } else if (this.contains("CANCELLED")) {
                            R.color.red
                        } else if (this.contains("SCHEDULE CHANGE")) {
                            R.color.yellow
                        } else if (this.contains("ON TIME")) {
                            R.color.green
                        } else {
                            // Cargo Only
                            R.color.black
                        }

                    val spanString = SpannableString(this)
                    spanString.setSpan(
                        ForegroundColorSpan(context.getColor(fontColor)),
                        0,
                        this.length,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    this@cell.status.text = spanString
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        parentContext = parent.context
        return FlightViewHolder(CellFlightBinding.inflate(LayoutInflater.from(parentContext), parent, false))
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        holder.bindView(getItem(position), parentContext)
    }

    override fun onCurrentListChanged(previousList: MutableList<FlightInfo>, currentList: MutableList<FlightInfo>) {
        super.onCurrentListChanged(previousList, currentList)
        onListChangedLambda?.invoke()
    }

    fun setOnListChangedLambda(l: () -> Unit) {
        onListChangedLambda = l
    }
}