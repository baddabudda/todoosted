package com.forgblord.todo_prototype.fragments.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.forgblord.todo_prototype.data.models.TimeRecord
import com.forgblord.todo_prototype.data.viewmodels.RecordCRUD
import com.forgblord.todo_prototype.databinding.FragmentTrackBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date

class StopWatch: Fragment() {
    private var _binding: FragmentTrackBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val recordModel: RecordCRUD by viewModels()

    private var _record: TimeRecord? = null

    private val args: StopWatchArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            val record = TimeRecord(
                record_id = 0,
                task_id = args.taskId,
                datetime_start = Date(),
                datetime_end = null
            )
            recordModel.addRecord(record)
        }

        binding.tvRecordId.text = args.taskId.toString()

        binding.btnStop.setOnClickListener {
            val toUpdate = _record!!.copy(datetime_end = Date())
            recordModel.updateRecord(toUpdate)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recordModel.record.collect { flowRecord ->
                    _record = flowRecord
                    println(_record)
//                    binding.tvRecordId.text = (_record?.record_id ?: "None").toString()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}