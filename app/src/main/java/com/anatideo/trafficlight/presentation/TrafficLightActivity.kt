package com.anatideo.trafficlight.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.anatideo.trafficlight.R
import com.anatideo.trafficlight.databinding.ActivityTrafficLightBinding
import java.util.*

class TrafficLightActivity : AppCompatActivity(), TrafficLightContract.View {

    private val presenter by lazy { TrafficLightPresenter(this) }
    private val binding by lazy { ActivityTrafficLightBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter.initTrafficLight()
    }

    override fun showGreenLight() {
        binding.lightThree.setBackground(R.drawable.ic_green_circle)
    }

    override fun showYellowLight() {
        binding.lightTwo.setBackground(R.drawable.ic_yellow_circle)
    }

    override fun showRedLight() {
        binding.lightOne.setBackground(R.drawable.ic_red_circle)
    }

    override fun turnOffAllLights() {
        with(binding) {
            lightOne.setBackground(R.drawable.ic_placeholder_circle)
            lightTwo.setBackground(R.drawable.ic_placeholder_circle)
            lightThree.setBackground(R.drawable.ic_placeholder_circle)
        }
    }

    private fun View.setBackground(resDrawable: Int) {
        background = ContextCompat.getDrawable(this@TrafficLightActivity, resDrawable)
    }
}