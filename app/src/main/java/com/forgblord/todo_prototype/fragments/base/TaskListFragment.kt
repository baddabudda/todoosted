package com.forgblord.todo_prototype.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
//import com.forgblord.todo_prototype.InboxFragmentDirections
import com.forgblord.todo_prototype.Task
import com.forgblord.todo_prototype.TaskListAdapter
import com.forgblord.todo_prototype.TaskListViewModel
import com.forgblord.todo_prototype.adapters.BaseListAdapter
import java.util.UUID

abstract class TaskListFragment<VB: ViewBinding> (): Fragment() {
    private var _binding: VB? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val taskListViewModel: TaskListViewModel by activityViewModels()
    abstract val data: MutableList<Task>

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract val getRecyclerView: (VB) -> RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)

        val recyclerView = getRecyclerView(binding)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = TaskListAdapter(data, { taskId -> onItemCheck(taskId)})
//        { taskId ->
//            findNavController().navigate(InboxFragmentDirections.openTask(taskId))
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onItemCheck(id: UUID) {
        taskListViewModel.removeTaskById(id)
    }
}