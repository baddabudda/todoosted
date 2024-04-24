package com.forgblord.todo_prototype.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.forgblord.todo_prototype.MainActivity
import com.forgblord.todo_prototype.R
import java.util.Timer
import java.util.TimerTask

class TimerBroadcast: Service() {
    companion object {
        // Channel ID for notifications
        const val CHANNEL_ID = "timer_notifications"

        // Actions
        const val START = "START"
        const val PAUSE = "PAUSE"
        const val RESET = "RESET"
        const val GET_STATUS = "STATUS"
        const val MOVE_FOREGROUND = "MOVE_FOREGROUND"
        const val MOVE_BACKGROUND = "MOVE_BACKGROUND"

        // Intent extras
        const val TIMER_ACTION = "TIMER_ACTION"
        const val TIME_ELAPSED = "TIME_ELAPSED"
        const val IS_RUNNING = "IS_TIMER_RUNNING"

        // Intent actions
        const val TIMER_TICK = "TIMER_TICK"
        const val TIMER_STATUS = "TIMER_STATUS"
    }

    private var timeElapsed: Int = 0
    private var timerRunning = false

    private var updateTimer = Timer()
    private var timer = Timer()

    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        getNotificationManager()

        // handling incoming intent action
        when (intent?.getStringExtra(TIMER_ACTION)!!) {
            START -> startTimer()
            PAUSE -> pauseTimer()
            RESET -> resetTimer()
            GET_STATUS -> sendStatus()
            MOVE_FOREGROUND -> moveForeground()
            MOVE_BACKGROUND -> moveBackground()
        }

        return START_STICKY
    }

    private fun moveForeground() {
        startForeground(1, buildNotification())

        if (timerRunning) {
            updateTimer = Timer()
            updateTimer.scheduleAtFixedRate(
                object : TimerTask() {
                    override fun run() {
                        updateNotification()
                    }
                },
                0,
                1000
            )
        }
    }

    private fun moveBackground() {
        updateTimer.cancel()
        stopForeground(true)
    }

    private fun startTimer() {
        timerRunning = true // update timer state

        sendStatus()

        timer = Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    val timerIntent = Intent()
                    timerIntent.action = TIMER_TICK

                    timeElapsed++

                    timerIntent.putExtra(TIME_ELAPSED, timeElapsed)
                    sendBroadcast(timerIntent)
                }
            },
            0,
            1000
        )
    }

    private fun resetTimer() {
        pauseTimer()
        timeElapsed = 0
        sendStatus()
    }

    private fun pauseTimer() {
        timer.cancel()
        timerRunning = false
        sendStatus()
    }

    // sending timer status using broadcast
    private fun sendStatus() {
        val statusIntent = Intent()
        statusIntent.action = TIMER_STATUS
        statusIntent.putExtra(IS_RUNNING, timerRunning)
        statusIntent.putExtra(TIME_ELAPSED, timeElapsed)
        sendBroadcast(statusIntent)
    }


    // notification-related blocks
    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            "TIMER",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun getNotificationManager() {
        notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager
    }

    private fun buildNotification(): Notification {
        val title = if (timerRunning) {
            "Timer is running!"
        } else {
            "Timer is paused!"
        }

        val hours = timeElapsed % 86400 / 3600
        val minutes = timeElapsed % 86400 % 3600 / 60
        val seconds = timeElapsed % 86400 % 3600 % 60
        val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setOngoing(true)
            .setContentText(time)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun updateNotification() {
        notificationManager.notify(
            1,
            buildNotification()
        )
    }

    override fun onDestroy() {
        timer.cancel()
        updateTimer.cancel()
        stopForeground(true)
        stopSelf()
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        timer.cancel()
        updateTimer.cancel()
        stopForeground(true)
        stopSelf()
        super.onTaskRemoved(rootIntent)
    }
}