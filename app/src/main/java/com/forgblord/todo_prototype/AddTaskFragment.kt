package com.forgblord.todo_prototype

import android.icu.text.DateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.databinding.FragmentTaskAddBinding
import com.forgblord.todo_prototype.dialogs.DatePickerFragment
import java.util.Date

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var date: Date? = null

        binding.apply {
            taskAddDate.text = "Not set"

            taskAddButton.setOnClickListener {
                val task =
                taskListViewModel.addTask(binding.taskAddTitle.text.toString(), date)
                findNavController().popBackStack()
            }

            taskAddDate.setOnClickListener {
                findNavController().navigate(AddTaskFragmentDirections.selectDate())
            }
        }

        setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE) { _, bundle ->
            date = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date

            if (date != null) {
                binding.taskAddDate.text = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(date)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val fb = (activity as MainActivity).getAddButton().show()
        _binding = null
    }
}