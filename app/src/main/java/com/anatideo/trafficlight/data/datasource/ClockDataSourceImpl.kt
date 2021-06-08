package com.anatideo.trafficlight.data.datasource

import java.util.*

class ClockDataSourceImpl : ClockDataSource {
    override fun getCurrentHour(): Int? = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
}