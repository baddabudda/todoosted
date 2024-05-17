package com.forgblord.todo_prototype.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.forgblord.todo_prototype.data.models.RecordTask
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.models.TimeRecord

@Dao
interface RecordDao {
    /*@Query("SELECT * FROM timerecord WHERE datetime_end == -1")
    suspend fun getActiveRecord(): TimeRecord*/
    @Transaction
    @Query(
        "SELECT * FROM timerecord WHERE datetime_end == -1"
    )
    suspend fun getActiveRecord(): List<RecordTask>

    @Insert
    suspend fun addRecord(record: TimeRecord)

    @Update
    suspend fun updateRecord(record: TimeRecord)

    /*@Delete
    suspend fun deleteRecord(record: TimeRecord)*/
}