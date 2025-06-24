package com.forgblord.todo_prototype.data

import androidx.room.TypeConverter
import java.util.Date
import kotlin.time.Duration.Companion.seconds

class TaskTypeConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long {
        return if (date != null) {
            date.time / 1000
        } else {
            -1
        }
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date? {
        return if (millisSinceEpoch == -1L) {
            null
        } else {
            Date(millisSinceEpoch * 1000)
        }
    }
}