package com.forgblord.todo_prototype.utils

import androidx.lifecycle.MutableLiveData
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class StopwatchCounter {
    private lateinit var timer: Timer

    private var duration: Duration = Duration.ZERO
    private var seconds = "00"
    private var minutes = "00"
    private var hours = "00"

    var state: State = State.STOPPED

    val timeString = MutableLiveData("00:00:00")

    fun start() {
        timer = fixedRateTimer(
            initialDelay = 1000L,
            period = 1000L
        ) {
            duration = duration.plus(1.seconds)
            println("Running")
            updateTimeUnits()
        }
    }

    fun stop() {
        if (this::timer.isInitialized) {
            timer.cancel()
        }
    }

    fun initTimeUnits(dur: Long, hh: Long, mm: Long, ss: Long) {
        duration = dur.seconds

        hours = hh.toString().padStart(2, '0')
        minutes = mm.toString().padStart(2, '0')
        seconds = ss.toString().padStart(2, '0')

        timeString.value = "${this.hours}:${this.minutes}:${this.seconds}"
    }

    fun resetTimeUnits() {
        duration = Duration.ZERO

        hours = "00"
        minutes = "00"
        seconds = "00"

        timeString.value = "${this.hours}:${this.minutes}:${this.seconds}"
    }

    private fun updateTimeUnits() {
        duration.toComponents { hours, minutes, seconds, _ ->
            this.hours = hours.toString().padStart(2, '0')
            this.minutes = minutes.toString().padStart(2, '0')
            this.seconds = seconds.toString().padStart(2, '0')

            timeString.postValue("${this.hours}:${this.minutes}:${this.seconds}")
        }
    }

    enum class State {
        STARTED,
        RESUMED,
        STOPPED
    }
}