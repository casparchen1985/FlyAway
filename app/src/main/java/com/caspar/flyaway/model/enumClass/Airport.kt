package com.caspar.flyaway.model.enumClass

enum class FlightType {
    Departure,
    Arrival;

    fun toAPICode(): String {
        return this.name[0].toString()
    }
}

enum class Airport {
    TPE,  // 桃園中正機場
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
}