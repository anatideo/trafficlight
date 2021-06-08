package com.anatideo.trafficlight.presentation

interface TrafficLightContract {
    interface View {
        fun showGreenLight()
        fun showYellowLight()
        fun showRedLight()
        fun turnOffAllLights()
    }

    interface Presenter {
        fun initTrafficLight()
    }
}