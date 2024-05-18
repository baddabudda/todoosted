package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.RecordTask
import com.forgblord.todo_prototype.data.models.TimeRecord
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class StopwatchModel(
//    val taskId: Int?,
): ViewModel() {
    private val todoRepository: TodoRepository = TodoRepository.getInstance()
    private val _record: MutableStateFlow<RecordTask?> = MutableStateFlow(null)
    val record: StateFlow<RecordTask?> = _record.asStateFlow()

    private var _taskId: Int = 0

    private lateinit var timer: Timer

    private var duration: Duration = Duration.ZERO
    private var seconds = "00"
    private var minutes = "00"
    private var hours = "00"
    val timeString = MutableLiveData("00:00:00")

    val state = MutableLiveData(State.STOPPED)

    init {
        viewModelScope.launch {
            val tmp = todoRepository.getActiveRecord()
            if (tmp.isNotEmpty()) {
                _record.value = tmp[0]
                duration = (Date().time / 1000L).seconds - (_record.value!!.record.datetime_start.time / 1000L).seconds
                startStopwatch()
            }
        }
    }

    private fun addRecord() {
        if (_taskId != 0) {
            val record = TimeRecord(
                record_id = 0,
                task_id = _taskId,
                datetime_start = Date(),
                datetime_end = null
            )

            viewModelScope.launch {
                todoRepository.addRecord(record)
                _record.value = todoRepository.getActiveRecord()[0]
                println("Adding. Record is : ${_record.value}")
            }
        }
    }

    private fun closeRecord(record: TimeRecord) {
        viewModelScope.launch {
            todoRepository.updateRecord(record)
            _record.value = null
            println("Closing. Record is : ${_record.value}")
        }
    }

    fun requestAction(action: Action, taskId: Int = 0) {
        println("Current state is: ${state.value}")
        when(action) {
            Action.START -> {
                if (state.value == State.RUNNING) cancelStopwatch(true)
                _taskId = taskId
                addRecord()
                startStopwatch()
            }
            Action.STOP -> {
                cancelStopwatch(true)
            }
        }
    }

    private fun startStopwatch() {
        state.value = State.RUNNING

        timer = fixedRateTimer(
            initialDelay = 1000L,
            period = 1000L
        ) {
            duration = duration.plus(1.seconds)
            updateTimeUnits()
        }
    }

    private fun cancelStopwatch(requireClose: Boolean) {
        state.value = State.STOPPED

        if (this::timer.isInitialized) {
            timer.cancel()
        }

        if (requireClose) {
            val time_end = Date()
            val time_dif = (time_end.time - _record.value!!.record.datetime_start.time) / 1000L
            println(_record.value!!.record)
            closeRecord(_record.value!!.record.copy(datetime_end = Date(), duration = time_dif))
        }

        resetTimeUnits()
    }

    private fun resetTimeUnits() {
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

    override fun onCleared() {
        super.onCleared()
        println("onCleared!")
    }

    enum class Action {
        START,
        STOP
    }

    enum class State {
        RUNNING,
        STOPPED
    }
}

/*
class StopWatchModelFactory(
    private val taskId: Int?,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StopwatchModel(taskId) as T
    }
}*/
