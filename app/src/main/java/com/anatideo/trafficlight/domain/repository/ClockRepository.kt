package com.anatideo.trafficlight.domain.repository

interface ClockRepository {
    fun getCurrentHour(): Int?
}