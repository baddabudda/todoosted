package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.TimeRecord
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecordCRUD: ViewModel() {
    private val todoRepository: TodoRepository = TodoRepository.getInstance()

    private val _record: MutableStateFlow<TimeRecord?> = MutableStateFlow(null)
    val record: StateFlow<TimeRecord?> = _record.asStateFlow()

    init {
        getActiveRecord()
    }

    fun addRecord(record: TimeRecord) {
        viewModelScope.launch {
            todoRepository.addRecord(record)
            getActiveRecord()
        }
    }

    fun updateRecord(record: TimeRecord) {
        viewModelScope.launch {
            todoRepository.updateRecord(record)
            getActiveRecord()
        }
    }

    private fun getActiveRecord() {
        viewModelScope.launch {
            _record.value = todoRepository.getActiveRecord()
        }
    }
}