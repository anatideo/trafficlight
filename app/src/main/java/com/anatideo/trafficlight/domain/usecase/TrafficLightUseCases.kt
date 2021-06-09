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
    fun getWaitingTimeStages(): List<Pair<TrafficStage, WaitingTime>> {
        val currentHour = clockRepository.getCurrentHour()
        val timeOfDay = timeOfDayMapper.map(currentHour)

        return when (timeOfDay) {
            TimeOfDay.DayTime -> listOf(
                    TrafficStage.CLOSE to WaitingTime(MAX),
                    TrafficStage.OPEN to WaitingTime(MEDIUM),
                    TrafficStage.SWITCH to WaitingTime(LOWEST)
            )
            TimeOfDay.RushTime -> listOf(
                    TrafficStage.CLOSE to WaitingTime(LOW),
                    TrafficStage.OPEN to WaitingTime(MAX),
                    TrafficStage.SWITCH to WaitingTime(LOWEST)
            )
            TimeOfDay.NightTime -> listOf(
                    TrafficStage.CLOSE to WaitingTime(LOWEST),
                    TrafficStage.OPEN to WaitingTime(MAX),
                    TrafficStage.SWITCH to WaitingTime(LOWEST)
            )
            TimeOfDay.Unknown -> listOf(
                    TrafficStage.SWITCH to WaitingTime(BLINKING),
                    TrafficStage.NONE to WaitingTime(BLINKING)
            )
        }
    }

    fun getWaitingTimeInterval(): Long = INTERVAL

    companion object {
        private const val MAX = 25000L
        private const val MEDIUM = 20000L
        private const val LOW = 15000L
        private const val LOWEST = 5000L
        private const val BLINKING = 500L
        private const val INTERVAL = 1000L
    }
}