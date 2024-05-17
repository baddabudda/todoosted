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
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.viewmodels.StopwatchModel
import com.forgblord.todo_prototype.databinding.FragmentTrackBinding
import kotlinx.coroutines.launch

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

        val buttonStateObserver = Observer<StopwatchModel.State> {
            when (it) {
                StopwatchModel.State.RUNNING -> {
                    binding.btnStart.visibility = View.GONE
                    binding.btnStop.visibility = View.VISIBLE
                }
                StopwatchModel.State.STOPPED -> {
                    binding.btnStart.visibility = View.VISIBLE
                    binding.btnStop.visibility = View.GONE
                }
            }
        }

        stopwatch.state.observe(viewLifecycleOwner, buttonStateObserver)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            println("New task id: ${bundle.getInt(taskKey)}")
            stopwatch.requestAction(StopwatchModel.Action.START, bundle.getInt(taskKey))
        }

        /*binding.btnStart.setOnClickListener {
            stopwatch.requestAction(StopwatchModel.Action.START, arguments?.getInt(taskKey))
        }*/

        binding.btnStop.setOnClickListener {
            stopwatch.requestAction(StopwatchModel.Action.STOP)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                stopwatch.record.collect { flowRecord ->
                    binding.tvTask.text = flowRecord?.let {
                         getString(R.string.stopwatch_task_tracking, it.task_id)
                    } ?: getString(R.string.stopwatch_task_none)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        stopwatch.requestAction(StopwatchModel.Action.STOP)
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Stopwatch destroyed")
    }
}