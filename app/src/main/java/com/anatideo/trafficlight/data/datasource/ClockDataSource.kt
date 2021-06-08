package com.anatideo.trafficlight.data.datasource

interface ClockDataSource {
    fun getCurrentHour(): Int?
}