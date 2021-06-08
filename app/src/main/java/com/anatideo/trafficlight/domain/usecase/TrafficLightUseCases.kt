package com.anatideo.trafficlight.domain.usecase

import com.anatideo.trafficlight.data.ClockRepositoryImpl
import com.anatideo.trafficlight.domain.mapper.TimeOfDayMapper
import com.anatideo.trafficlight.domain.model.TimeOfDay
import com.anatideo.trafficlight.domain.model.WaitingTime
import com.anatideo.trafficlight.domain.repository.ClockRepository
import com.anatideo.trafficlight.domain.model.TrafficStage

class TrafficLightUseCases(
        private val clockRepository: ClockRepository = ClockRepositoryImpl(),
        private val timeOfDayMapper: TimeOfDayMapper = TimeOfDayMapper()
) {
    fun getWaitingTimeStages(): Map<TrafficStage, WaitingTime> {
        val currentHour = clockRepository.getCurrentHour()
        val timeOfDay = timeOfDayMapper.map(currentHour)

        return when (timeOfDay) {
            TimeOfDay.DayTime -> mapOf(
                    TrafficStage.CLOSE to WaitingTime(20000L),
                    TrafficStage.OPEN to WaitingTime(15000L),
                    TrafficStage.SWITCH to WaitingTime(5000L)
            )
            TimeOfDay.RushTime -> mapOf(
                    TrafficStage.CLOSE to WaitingTime(10000L),
                    TrafficStage.OPEN to WaitingTime(25000L),
                    TrafficStage.SWITCH to WaitingTime(5000L)
            )
            TimeOfDay.NightTime -> mapOf(
                    TrafficStage.CLOSE to WaitingTime(5000L),
                    TrafficStage.OPEN to WaitingTime(30000L),
                    TrafficStage.SWITCH to WaitingTime(5000L)
            )
            TimeOfDay.Unknown -> mapOf(
                    TrafficStage.SWITCH to WaitingTime(500L),
                    TrafficStage.NONE to WaitingTime(500L)
            )
        }
    }

    fun getInterval(): Long = INTERVAL_COUNT

    companion object {
        private const val INTERVAL_COUNT = 1000L
    }
}