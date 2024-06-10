package com.caspar.flyaway.model.enumClass

enum class FlightType {
    Departure,
    Arrival;

    fun toAPICode(): String {
        return this.name[0].toString()
    }
}

enum class Airport {
    TPE,  // 台北桃園國際機場
    CYI,  // 嘉義
    CMJ,  // 七美
    GNI,  // 綠島
    HUN,  // 花蓮
    KHH,  // 高雄
    KNH,  // 金門
    MZG,  // 馬公
    MFK,  // 馬祖
    KYD,  // 蘭嶼
    PIF,  // 屏東
    WOT,  // 望安
    TSA,  // 松山
    TXG,  // 台中
    TTT,  // 台東
    TNN;  // 台南

    fun toAPICode(): String {
        return this.name
    }

    fun getAllDisplayName(): List<String> {
        return listOf(
            "TPE - 台北桃園國際機場",
            "CYI - 嘉義",
            "CMJ - 七美",
            "GNI - 綠島",
            "HUN - 花蓮",
            "KHH - 高雄",
            "KNH - 金門",
            "MZG - 馬公",
            "MFK - 馬祖",
            "KYD - 蘭嶼",
            "PIF - 屏東",
            "WOT - 望安",
            "TSA - 松山",
            "TXG - 台中",
            "TTT - 台東",
            "TNN - 台南"
        )
    }

    fun getItemByPosition(position: Int): Airport {
        return when (position) {
            1 -> Airport.CYI
            2 -> Airport.CMJ
            3 -> Airport.GNI
            4 -> Airport.HUN
            5 -> Airport.KHH
            6 -> Airport.KNH
            7 -> Airport.MZG
            8 -> Airport.MFK
            9 -> Airport.KYD
            10 -> Airport.PIF
            11 -> Airport.WOT
            12 -> Airport.TSA
            13 -> Airport.TXG
            14 -> Airport.TTT
            15 -> Airport.TNN
            else -> Airport.TPE
        }
    }
}