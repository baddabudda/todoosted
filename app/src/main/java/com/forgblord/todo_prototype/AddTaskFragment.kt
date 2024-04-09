package com.forgblord.todo_prototype

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.databinding.FragmentTaskAddBinding

class AddTaskFragment : Fragment() {
    private var _binding: FragmentTaskAddBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    val taskListViewModel: TaskListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskAddBinding.inflate(inflater, container, false)
        val fb = (activity as MainActivity).getAddButton().hide()

        binding.taskAddButton.setOnClickListener {
            taskListViewModel.addTask(binding.taskAddTitle.text.toString())
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val fb = (activity as MainActivity).getAddButton().show()
        _binding = null
    }
}