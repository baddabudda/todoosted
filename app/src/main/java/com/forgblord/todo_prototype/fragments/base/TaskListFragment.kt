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
import com.forgblord.todo_prototype.Task
import com.forgblord.todo_prototype.TaskListViewModel
import com.forgblord.todo_prototype.adapters.TaskListAdapter
import com.forgblord.todo_prototype.interfaces.TaskInterface
import java.util.UUID

abstract class TaskListFragment<VB: ViewBinding> (): Fragment(), TaskInterface {
    private var _binding: VB? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    abstract val data: MutableList<Task>
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract val getRecyclerView: (VB) -> RecyclerView
    abstract val getOnClickNavigation: (id: UUID) -> Unit

    protected val taskListViewModel: TaskListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)

        val recyclerView = getRecyclerView(binding)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = TaskListAdapter(data, this, getOnClickNavigation)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun removeById(id: UUID) {
        taskListViewModel.removeTaskById(id)
    }

}