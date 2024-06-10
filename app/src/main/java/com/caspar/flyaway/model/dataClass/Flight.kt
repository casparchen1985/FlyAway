package com.caspar.flyaway.model.dataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlightInfo(
    @SerializedName("FlyType")
    val flyType: String = "",
    @SerializedName("AirlineID")
    val airlineId: String = "",
    @SerializedName("Airline")
    val airline: String = "",
    @SerializedName("FlightNumber")
    val flightNumber: String = "",
    @SerializedName("DepartureAirportID")
    val departureAirportId: String = "",
    @SerializedName("DepartureAirport")
    val departureAirport: String = "",
    @SerializedName("ArrivalAirportID")
    val arrivalAirportId: String = "",
    @SerializedName("ArrivalAirport")
    val arrivalAirport: String = "",
    @SerializedName("ScheduleTime")
    val scheduleTime: String = "",
    @SerializedName("ActualTime")
    val actualTime: String = "",
    @SerializedName("EstimatedTime")
    val estimatedTime: String = "",
    @SerializedName("Remark")
    val remark: String = "",
    @SerializedName("Terminal")
    val terminal: String = "",
    @SerializedName("Gate")
    val gate: String? = null,
    @SerializedName("UpdateTime")
    val updateTime: String = "",
) : Parcelable