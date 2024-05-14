package com.forgblord.todo_prototype.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.databinding.DialogPriorityBinding

class PriorityDialog: DialogFragment() {
    private var _binding: DialogPriorityBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val onClick = View.OnClickListener { view ->
        val priority: String = (view as TextView).text.toString()
        onPriorityPicked(priority)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPriorityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onPriorityPicked(priority: String) {
        val id = when(priority) {
            getString(R.string.priority_1) -> 1
            getString(R.string.priority_2) -> 2
            getString(R.string.priority_3) -> 3
            else -> 4
        }

        val text = priority.substringBefore(" ")

        setFragmentResult(REQUEST_KEY_PRIORITY, bundleOf(BUNDLE_KEY_ID to id,
                                                            BUNDLE_KEY_TITLE to text))



        dismiss()
    }

    companion object {
        const val REQUEST_KEY_PRIORITY = "REQUEST_KEY_PRIORITY"
        const val BUNDLE_KEY_ID = "BUNDLE_KEY_ID"
        const val BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE"
    }
}