package com.caspar.flyaway.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
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
        private val grayDarkWords = listOf("DEPARTED", "ARRIVED", "出發", "已飛", "已到")
        private val redWords = listOf("CANCELLED", "取消", "延誤", "DELAY", "關閉")
        private val yellowWords = listOf("SCHEDULE CHANGE", "更改")
        private val greenWords = listOf("ON TIME", "準時")
        private val blueWords = listOf("登機")

        @SuppressLint("SetTextI18n")
        fun bindView(info: FlightInfo, context: Context) {
            with(binding) cell@{
                airline.text = info.airline
                airlineId.text = info.airlineId
                flightNumber.text = info.flightNumber
                terminal.text = (info.terminal ?: "N/A") + " 航廈"
                gate.text = (info.gate ?: "N/A") + " 登機門"
                departureAirport.text = info.departureAirport + "(" + info.departureAirportId + ")"
                arrivalAirport.text = info.arrivalAirport + "(" + info.arrivalAirportId + ")"
                scheduleTime.text = info.scheduleTime
                estimateTime.text = info.estimatedTime
                actualTime.text = info.actualTime
                with(info.remark) {
                    val fontColor =
                        when {
                            doesContainKeyword(grayDarkWords, this) -> R.color.gray_dark
                            doesContainKeyword(redWords, this) -> R.color.red
                            doesContainKeyword(yellowWords, this) -> R.color.yellow
                            doesContainKeyword(greenWords, this) -> R.color.green
                            doesContainKeyword(blueWords, this) -> R.color.blue
                            else -> R.color.black
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

        private fun doesContainKeyword(stringList: List<String>, keyword: String): Boolean {
            Log.d("cas", "doesContainKeyword keyword:$keyword")
            stringList.forEach {
                Log.d("cas", "forEach item:$it")
                if (keyword.contains(it)) return true
            }
            return false
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