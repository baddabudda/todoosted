package com.forgblord.todo_prototype.fragments.record

/*
import android.icu.text.DateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.MainActivity
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.databinding.FragmentRecordBinding
import com.forgblord.todo_prototype.fragments.datepicker.DatePickerFragment
import com.forgblord.todo_prototype.fragments.timepicker.TimePickerFragment
import java.util.Date

class RecordFragment: Fragment() {
    private var _binding: FragmentRecordBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private var pendingDateView: TextView? = null
    private val toDatePicker = View.OnClickListener {
        findNavController().navigate(R.id.add_date)
        pendingDateView = it as TextView
    }

    private var pendingTimeView: TextView? = null
    private val toTimePicker = View.OnClickListener {
        findNavController().navigate(R.id.add_time)
        pendingTimeView = it as TextView
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvStartDate.setOnClickListener(toDatePicker)
            tvEndDate.setOnClickListener(toDatePicker)

            tvStartTime.setOnClickListener(toTimePicker)
            tvEndTime.setOnClickListener(toTimePicker)
        }

        setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE) { _, bundle ->
            val date = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            pendingDateView?.text = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(date)

            */
/*if (date != null) {
                pendingDateView?.text = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(date)
            }*//*

        }

        setFragmentResultListener(TimePickerFragment.REQUEST_KEY_TIME) { _, bundle ->
            val hours = bundle.getInt(TimePickerFragment.BUNDLE_KEY_HOURS).toString().padStart(2, '0')
            val minutes = bundle.getInt(TimePickerFragment.BUNDLE_KEY_MINUTES).toString().padStart(2, '0')
            pendingTimeView?.text = "$hours:$minutes"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/
