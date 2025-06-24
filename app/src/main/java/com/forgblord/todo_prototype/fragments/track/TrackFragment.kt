package com.forgblord.todo_prototype.fragments.track

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.databinding.FragmentTodayBinding
import com.forgblord.todo_prototype.databinding.FragmentTrackBinding
import com.forgblord.todo_prototype.services.TimerBroadcast

class TrackFragment: Fragment() {
    private var _binding: FragmentTrackBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private var timerRunning = false
    private lateinit var statusReceiver: BroadcastReceiver
    private lateinit var timeReceiver: BroadcastReceiver

    private var isResetCheck = false

    override fun onStart() {
        super.onStart()
        moveBackground()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackBinding.inflate(inflater, container, false)

        binding.btnStart.setOnClickListener {
            if (timerRunning) pauseTimer()
            else startTimer()
        }

        /*binding.btnStop.setOnClickListener {
            resetTimer()
        }*/

        return binding.root
    }

    override fun onResume() {
        super.onResume()

//        getTimerStatus() // starting foreground service

        val statusFilter = IntentFilter() // setting up intent filter (send status)
        statusFilter.addAction(TimerBroadcast.TIMER_STATUS)
        statusReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val isRunning = p1?.getBooleanExtra(TimerBroadcast.IS_RUNNING, false)!!
                timerRunning = isRunning
                val timeElapsed = p1.getIntExtra(TimerBroadcast.TIME_ELAPSED, 0)

//                updateLayout(isTimerRunning)
                updateTimerLayout(timeElapsed)
            }
        }
        requireActivity().registerReceiver(statusReceiver, statusFilter, Context.RECEIVER_NOT_EXPORTED)

        val timeFilter = IntentFilter() // setting up intent filter (start timer)
        timeFilter.addAction(TimerBroadcast.TIMER_TICK)
        timeReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val timeElapsed = p1?.getIntExtra(TimerBroadcast.TIME_ELAPSED, 0)!!
                updateTimerLayout(timeElapsed)
            }
        }
        requireActivity().registerReceiver(timeReceiver, timeFilter, Context.RECEIVER_NOT_EXPORTED)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(statusReceiver)
        requireActivity().unregisterReceiver(timeReceiver)

//        moveForeground()
    }

    private fun moveForeground() {
        if (!isResetCheck) {
            val timerService = Intent(requireContext(), TimerBroadcast::class.java)
            timerService.putExtra(TimerBroadcast.TIMER_ACTION, TimerBroadcast.MOVE_FOREGROUND)

            ContextCompat.startForegroundService(requireContext(), timerService)
        }
    }

    private fun updateTimerLayout(timeElapsed: Int) {
        val hours = timeElapsed % 86400 / 3600
        val minutes = timeElapsed % 86400 % 3600 / 60
        val seconds = timeElapsed % 86400 % 3600 % 60
        binding.tvStopwatch.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun getTimerStatus() {
        val timerService = Intent(requireActivity(), TimerBroadcast::class.java)
        timerService.putExtra(TimerBroadcast.TIMER_ACTION, TimerBroadcast.GET_STATUS)
        requireActivity().startService(timerService)
    }

    private fun moveBackground() {
        val timerService = Intent(requireActivity(), TimerBroadcast::class.java)
        timerService.putExtra(TimerBroadcast.TIMER_ACTION, TimerBroadcast.MOVE_BACKGROUND)
        requireActivity().startService(timerService)
    }

    private fun startTimer() {
        isResetCheck = false
        val timerService = Intent(requireActivity(), TimerBroadcast::class.java)
        timerService.putExtra(TimerBroadcast.TIMER_ACTION, TimerBroadcast.START)
        requireActivity().startService(timerService)
    }

    private fun pauseTimer() {
        isResetCheck = false
        val timerService = Intent(requireActivity(), TimerBroadcast::class.java)
        timerService.putExtra(TimerBroadcast.TIMER_ACTION, TimerBroadcast.PAUSE)
        requireActivity().startService(timerService)
    }

    private fun resetTimer() {
        isResetCheck = true
        val timerService = Intent(requireActivity(), TimerBroadcast::class.java)
        timerService.putExtra(TimerBroadcast.TIMER_ACTION, TimerBroadcast.RESET)
        requireActivity().startService(timerService)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}