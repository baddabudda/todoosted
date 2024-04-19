package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.viewmodels.TaskVM
import com.forgblord.todo_prototype.data.viewmodels.TaskViewModel
import com.forgblord.todo_prototype.databinding.FragmentTasklistBinding
import com.forgblord.todo_prototype.fragments.tasklist.adapter.TaskListAdapter
import kotlinx.coroutines.launch

abstract class TaskListFragment(): Fragment() {
    private var _binding: FragmentTasklistBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    protected val taskListViewModel: TaskViewModel by viewModels()
    private val taskModel: TaskVM by viewModels()

    abstract fun openTask(taskId: Int)
    abstract fun initializeList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasklistBinding.inflate(inflater, container, false)
        binding.rvTaskList.layoutManager = LinearLayoutManager(context)

        initializeList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                taskListViewModel.taskList.collect { list ->
                    binding.rvTaskList.adapter = TaskListAdapter(list,
                        { task -> updateOnCheck(task)},
                        { taskId -> openTask(taskId) }
                    )
                }
            }
        }
    }

    private fun updateOnCheck(task: Task) {
        taskModel.updateTask(task)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("LIST", "VIEW HAS BEEN DESTROYED")
        _binding = null
    }
}