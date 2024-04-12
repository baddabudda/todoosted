package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.util.UUID

abstract class TaskListFragment<VB: ViewBinding> (): Fragment() {
//    abstract val data: MutableList<Task>
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract val getRecyclerView: (VB) -> RecyclerView
    abstract val getOnClickNavigation: (id: UUID) -> Unit

    private var _binding: VB? = null
    protected val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val recyclerView = getRecyclerView(binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)

        val recyclerView = getRecyclerView(binding)
        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = TaskListAdapter(data, this, getOnClickNavigation)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

/*    override fun removeById(id: UUID) {
        taskListViewModel.removeTaskById(id)
    }*/

}