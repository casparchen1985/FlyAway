package com.caspar.flyaway.model

import com.caspar.flyaway.R
import com.caspar.flyaway.di.FlyAwayApplication

enum class ServerUrl {
    Flight,
    Currency;

    val url: String
        get() {
            return FlyAwayApplication.appContext.getString(
                if (this.name == "Flight") R.string.fly_api_url else R.string.currency_api_url
            )
        }
}