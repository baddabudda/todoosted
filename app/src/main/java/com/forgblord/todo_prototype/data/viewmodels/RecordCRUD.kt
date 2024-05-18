package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.TimeRecord
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class RecordCRUD: ViewModel() {
    protected val todoRepository: TodoRepository = TodoRepository.getInstance()

    fun addRecord(record: TimeRecord) {
        viewModelScope.launch {
            todoRepository.addRecord(record)
        }
    }

    fun updateRecord(record: TimeRecord) {
        viewModelScope.launch {
            todoRepository.updateRecord(record)
        }
    }

    fun deleteRecord(record: TimeRecord) {
        viewModelScope.launch {
            todoRepository.deleteRecord(record)
        }
    }
}