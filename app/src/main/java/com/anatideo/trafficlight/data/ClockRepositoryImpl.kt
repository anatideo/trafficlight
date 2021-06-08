package com.anatideo.trafficlight.data

import com.anatideo.trafficlight.data.datasource.ClockDataSource
import com.anatideo.trafficlight.data.datasource.ClockDataSourceImpl
import com.anatideo.trafficlight.domain.repository.ClockRepository

class ClockRepositoryImpl(
        private val clockDataSource: ClockDataSource = ClockDataSourceImpl()
) : ClockRepository {
    override fun getCurrentHour(): Int? = clockDataSource.getCurrentHour()
}