package com.forgblord.todo_prototype.fragments.timepicker

/*
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.forgblord.todo_prototype.fragments.datepicker.DatePickerFragment
import java.util.Calendar

class TimePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val timeListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            setFragmentResult(REQUEST_KEY_TIME, bundleOf(BUNDLE_KEY_HOURS to hour,
                                                            BUNDLE_KEY_MINUTES to minute))
        }

        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it.
        return TimePickerDialog(activity,
            timeListener,
            hour,
            minute,
            true
        )
    }

    companion object {
        const val REQUEST_KEY_TIME = "REQUEST_KEY_TIME"
        const val BUNDLE_KEY_HOURS = "BUNDLE_KEY_HOURS"
        const val BUNDLE_KEY_MINUTES = "BUNDLE_KEY_MINUTES"
    }
}*/
