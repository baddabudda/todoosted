package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.RecordTask
import com.forgblord.todo_prototype.data.models.TaskRecord
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecordDetailViewModel(
    recordId: Int,
): RecordCRUD() {
    private val _record: MutableStateFlow<TaskRecord?> = MutableStateFlow(null)
    val record: StateFlow<TaskRecord?> = _record.asStateFlow()

    init {
        viewModelScope.launch {
            _record.value = todoRepository.getRecordById(recordId)
        }
    }

    fun saveRecord() {
        updateRecord(_record.value!!.record)
    }

    fun deleteRecord() {
        deleteRecord(_record.value!!.record)
    }
}

class RecordDetailViewModelFactory(
    private val recordId: Int,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecordDetailViewModel(recordId) as T
    }
}