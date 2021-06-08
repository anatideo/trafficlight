package com.anatideo.trafficlight.presentation

import android.os.CountDownTimer
import android.util.Log
import com.anatideo.trafficlight.domain.model.TrafficStage
import com.anatideo.trafficlight.domain.model.WaitingTime
import com.anatideo.trafficlight.domain.usecase.TrafficLightUseCases

class TrafficLightPresenter(
        private val view: TrafficLightContract.View,
        private val trafficLightUseCases: TrafficLightUseCases = TrafficLightUseCases()
) : TrafficLightContract.Presenter {

    override fun initTrafficLight() {
        with(trafficLightUseCases) {
            val waitingTimeStages = getWaitingTimeStages()
            val interval = getInterval()

            startTrafficLight(
                    waitingTimeStages = waitingTimeStages,
                    interval = interval,
            )
        }
    }

    private fun startTrafficLight(
            waitingTimeStages: Map<TrafficStage, WaitingTime>,
            interval: Long,
            currentIndex: Int = 0
    ) {
        val currentKey = waitingTimeStages.entries.toTypedArray()[currentIndex]
        val trafficStage = currentKey.key
        val count = currentKey.value.count

        setCurrentTrafficLight(trafficStage)

        val timer = object: CountDownTimer(count, interval) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("onTick", millisUntilFinished.toString())
            }

            override fun onFinish() {
                val nextIndex = currentIndex.inc()
                if (nextIndex <= waitingTimeStages.size.dec()) {
                    startTrafficLight(waitingTimeStages, interval, nextIndex)
                } else {
                    initTrafficLight()
                }
            }
        }

        timer.start()
    }

    private fun setCurrentTrafficLight(trafficStage: TrafficStage) {
        view.turnOffAllLights()

        when (trafficStage) {
            TrafficStage.CLOSE -> view.showRedLight()
            TrafficStage.SWITCH -> view.showYellowLight()
            TrafficStage.OPEN -> view.showGreenLight()
            TrafficStage.NONE -> view.turnOffAllLights()
        }
    }
}