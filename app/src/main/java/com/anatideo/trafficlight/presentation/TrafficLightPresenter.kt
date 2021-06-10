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
            val interval = getWaitingTimeInterval()

            startTrafficLightSequence(waitingTimeStages, interval)
        }
    }

    private fun startTrafficLightSequence(
            waitingTimeStages: List<Pair<TrafficStage, WaitingTime>>,
            interval: Long,
            currentIndex: Int = 0
    ) {
        val current = waitingTimeStages[currentIndex]

        setCurrentTrafficLight(trafficStage = current.first)

        val timer = object: CountDownTimer(current.second.count, interval) {
            override fun onTick(remainingMillis: Long) {
                Log.d("time goes by :_(", remainingMillis.toString())
            }

            override fun onFinish() {
                val nextIndex = currentIndex.inc()

                if (nextIndex <= waitingTimeStages.size.dec()) {
                    startTrafficLightSequence(
                            waitingTimeStages,
                            interval,
                            nextIndex
                    )
                } else {
                    // refresh it cause iteration is over
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