package com.forgblord.todo_prototype.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.forgblord.todo_prototype.data.models.TimeRecord

@Dao
interface RecordDao {
    @Query("SELECT * FROM timerecord WHERE datetime_end == -1")
    suspend fun getActiveRecord(): TimeRecord

    @Insert
    suspend fun addRecord(record: TimeRecord)

    @Update
    suspend fun updateRecord(record: TimeRecord)

    /*@Delete
    suspend fun deleteRecord(record: TimeRecord)*/
}