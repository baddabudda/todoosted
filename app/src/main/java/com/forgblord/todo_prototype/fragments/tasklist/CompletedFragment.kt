package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.NavBottombarDirections
import com.forgblord.todo_prototype.data.viewmodels.CompletedViewModel
import com.forgblord.todo_prototype.fragments.tasklist.adapter.TaskListAdapter
import com.forgblord.todo_prototype.utils.popOnBackPress
import kotlinx.coroutines.launch

class CompletedFragment: TaskListFragment() {
    private val completedViewModel: CompletedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popOnBackPress(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                completedViewModel.taskList.collect { list ->
                    binding.rvTaskList.adapter = TaskListAdapter(list,
                        { task -> updateOnCheck(task)},
                        { taskId -> openTask(taskId) }
                    )
                }
            }
        }
    }
}