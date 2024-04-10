package com.forgblord.todo_prototype.legacy

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.databinding.FragmentInboxBinding
import java.util.UUID

//class InboxFragment: Fragment(), TaskListAdapter.OnItemCheckedListener {
//    private var _binding: FragmentInboxBinding? = null
//    private val binding
//        get() = checkNotNull(_binding) {
//            "Cannot access binding because it is null. Is the view visible?"
//        }
//
//    private val taskListViewModel: TaskListViewModel by activityViewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val animation = TransitionInflater.from(requireContext()).inflateTransition(
//            android.R.transition.no_transition
//        )
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentInboxBinding.inflate(inflater, container, false)
//        binding.rvInboxList.layoutManager = LinearLayoutManager(context)
//
//        val tasks = taskListViewModel.getAllTasks()
//        binding.rvInboxList.adapter = TaskListAdapter(tasks, this) { taskId ->
//            findNavController().navigate(InboxFragmentDirections.openTask(taskId))
//        }
//
//        return binding.root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    override fun onItemCheck(id: UUID) {
//        taskListViewModel.removeTaskById(id)
//    }
//}