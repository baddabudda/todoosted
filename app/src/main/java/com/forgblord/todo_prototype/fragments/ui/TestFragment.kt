package com.forgblord.todo_prototype.fragments.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.Task
import com.forgblord.todo_prototype.TaskListViewModel
import com.forgblord.todo_prototype.adapters.TaskListAdapter
import com.forgblord.todo_prototype.databinding.FragmentTestBinding
import com.forgblord.todo_prototype.interfaces.TaskInterface
import java.util.UUID

//class TestFragment: Fragment(), TaskInterface {
//    private var _binding: FragmentTestBinding? = null
//    private val binding
//        get() = checkNotNull(_binding) {
//            "Cannot access binding because it is null. Is the view visible?"
//        }
//
//    private val taskListViewModel: TaskListViewModel by activityViewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentTestBinding.inflate(inflater, container, false)
//        binding.rvTestInbox.layoutManager = LinearLayoutManager(context)
//        binding.rvTestToday.layoutManager = LinearLayoutManager(context)
//
//        val inbox = taskListViewModel.getAllTasks()
//        val today = taskListViewModel.getAllDueToday()
//
//        binding.rvTestInbox.adapter = TaskListAdapter(inbox, this)
//        binding.rvTestToday.adapter = TaskListAdapter(today, this)
//
//        return binding.root
//    }
//
//    override fun removeById(id: UUID) {
//        taskListViewModel.removeTaskById(id)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}