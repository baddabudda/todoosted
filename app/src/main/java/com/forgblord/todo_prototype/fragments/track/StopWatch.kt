package com.forgblord.todo_prototype.fragments.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.models.TimeRecord
import com.forgblord.todo_prototype.data.viewmodels.RecordCRUD
import com.forgblord.todo_prototype.data.viewmodels.StopwatchModel
import com.forgblord.todo_prototype.databinding.FragmentTrackBinding
import com.forgblord.todo_prototype.utils.StopwatchCounter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date

class StopWatch: Fragment() {
    private var _binding: FragmentTrackBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

//    private val args: StopWatchArgs by navArgs()

    private val stopwatch: StopwatchModel by activityViewModels()

    companion object {
        const val taskKey = "TASK"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackBinding.inflate(inflater, container, false)

        val timeObserver = Observer<String> { newTime ->
            binding.tvStopwatch.text = newTime
        }

        stopwatch.timeString.observe(viewLifecycleOwner, timeObserver)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("New task id: ${arguments?.getInt(taskKey)}")

        binding.btnStart.setOnClickListener {
            stopwatch.requestAction(StopwatchModel.Action.START, arguments?.getInt(taskKey))
        }

        binding.btnStop.setOnClickListener {
            stopwatch.requestAction(StopwatchModel.Action.STOP)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                stopwatch.record.collect { flowRecord ->
                    binding.tvTaskId.text = requireContext().getString(R.string.stopwatch_test_task_id, flowRecord?.task_id)
                    binding.tvRecordId.text = requireContext().getString(R.string.stopwatch_test_record_id, flowRecord?.record_id)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}