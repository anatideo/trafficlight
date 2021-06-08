package com.anatideo.trafficlight.domain.mapper

import com.anatideo.trafficlight.domain.model.TimeOfDay

class TimeOfDayMapper {
    fun map (source: Int?): TimeOfDay {
        return when (source) {
            in 6..18 -> TimeOfDay.DayTime
            in 18..19 -> TimeOfDay.RushTime
            in 0..6, in 19..23 -> TimeOfDay.NightTime
            else -> TimeOfDay.Unknown
        }
    }
}