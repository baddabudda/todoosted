package com.forgblord.todo_prototype.fragments.record_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.data.models.TaskRecord
import com.forgblord.todo_prototype.data.viewmodels.RecordDetailViewModel
import com.forgblord.todo_prototype.data.viewmodels.RecordDetailViewModelFactory
import com.forgblord.todo_prototype.databinding.FragmentRecordInfoBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.time.Duration.Companion.seconds

class RecordDetailFragment: Fragment() {
    private var _binding: FragmentRecordInfoBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val recordDetailViewModel: RecordDetailViewModel by viewModels {
        RecordDetailViewModelFactory(requireArguments().getInt(recordKey))
    }

    companion object {
        const val recordKey = "RECORD_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            recordDetailViewModel.saveRecord()
            findNavController().popBackStack()
        }

        binding.btnDelete.setOnClickListener {
            recordDetailViewModel.deleteRecord()
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recordDetailViewModel.record.collect { flowrecord ->
                    flowrecord?.let { updateUI(it) }
                }
            }
        }
    }

    private fun updateUI(record: TaskRecord) {
        binding.tvTask.text = record.taskTitle
        binding.tvStartDate.text = SimpleDateFormat("EEE, MMM, dd").format(record.record.datetime_start)
        binding.tvStartTime.text = SimpleDateFormat("HH:mm:ss").format(record.record.datetime_start)
        binding.tvEndDate.text = SimpleDateFormat("EEE, MMM, dd").format(record.record.datetime_end)
        binding.tvEndTime.text = SimpleDateFormat("HH:mm:ss").format(record.record.datetime_end)

        val elapsedTime = record.record.duration!!.seconds
        val hrs = elapsedTime.inWholeHours.toString().padStart(2, '0')
        val mins = elapsedTime.inWholeMinutes.toString().padStart(2, '0')
        val seconds = elapsedTime.inWholeSeconds.toString().padStart(2, '0')
        binding.tvRecordDuration.text = "$hrs:$mins:$seconds"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}