package com.forgblord.todo_prototype.fragments.task_detail

import android.icu.text.DateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.viewmodels.TaskDetailViewModel
import com.forgblord.todo_prototype.data.viewmodels.TaskDetailViewModelFactory
import com.forgblord.todo_prototype.databinding.FragmentTaskDetailsBinding
import com.forgblord.todo_prototype.fragments.datepicker.DatePickerFragment
import com.forgblord.todo_prototype.fragments.projectpicker.ProjectPickerDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import java.util.Date

class TaskDetailDialog: BottomSheetDialogFragment() {
    private val args: TaskDetailDialogArgs by navArgs()

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val taskDetailViewModel: TaskDetailViewModel by viewModels {
        TaskDetailViewModelFactory(args.taskId)
    }

    private var _task: Task? = null

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        dialog?.let {
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout

            val layoutParams = bottomSheet.layoutParams
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            bottomSheet.layoutParams = layoutParams

            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            behavior.apply {
                peekHeight = (400 * density).toInt()

                addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {

                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {

                    }
                })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            taskName.doOnTextChanged { text, _, _, _ ->
                taskDetailViewModel.updateTask { oldTask ->
                    oldTask.copy(title=text.toString())
                }
            }

            /*cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                taskDetailViewModel.updateTask { oldTask ->
                    oldTask.copy(completed=isChecked)
                }
            }*/

            cbCompleted.setOnClickListener {
                taskDetailViewModel.updateTask { oldTask ->
                    oldTask.copy(completed=!oldTask.completed)
                }

                dismiss()
            }

            chipDate.setOnClickListener {
                findNavController().navigate(TaskDetailDialogDirections.selectDate())
            }

            btnDeleteTask.setOnClickListener {
                _task?.let { it1 -> taskDetailViewModel.deleteTask(it1) }
                findNavController().popBackStack()
            }

            projectName.setOnClickListener {
                findNavController().navigate(TaskDetailDialogDirections.actionTaskDetailToProjectPicker())
            }

            setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE) { _, bundle ->
                val date = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
                taskDetailViewModel.updateTask { task: Task ->
                    task.copy(date=date)
                }
            }

            setFragmentResultListener(ProjectPickerDialog.REQUEST_KEY_PROJECT) { _, bundle ->
                val projId = bundle.getInt(ProjectPickerDialog.BUNDLE_KEY_ID)
                val projectTitle = bundle.getString(ProjectPickerDialog.BUNDLE_KEY_TITLE)
                Log.d("DIALOG", projectTitle.toString())

                projectName.text = projectTitle.toString()
                taskDetailViewModel.updateTask { task: Task ->
                    task.copy(proj_id=projId)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                taskDetailViewModel.task.collect { flowTask ->
                    flowTask?.let { updateUI(it) }
                    _task = flowTask
                }
            }
        }
    }

    private fun updateUI(task: Task) {
        binding.apply {
            projectName.text = task.proj_id.toString()

            if (taskName.text.toString() != task.title) {
                taskName.setText(task.title)
            }

            chipDate.text = if (task.date == null) "Not set"
            else DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY)
                .format(task.date)

            cbCompleted.isChecked = task.completed
        }
    }
}