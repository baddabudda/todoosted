package com.forgblord.todo_prototype.fragments.dialogs

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.databinding.FragmentColorpickerBinding
import com.forgblord.todo_prototype.utils.ProjectColors

class ColorPickerDialog: DialogFragment() {
    private var _binding: FragmentColorpickerBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        val dialogWidth = 300 // specify a value here
        val dialogHeight = 300 // specify a value here

        dialog?.window?.apply {
            setLayout((dialogWidth * density).toInt(), (dialogHeight * density).toInt())
            setGravity(Gravity.CENTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentColorpickerBinding.inflate(inflater, container, false)
        binding.rvColors.layoutManager = LinearLayoutManager(context)
        binding.rvColors.adapter = ColorListAdapter(ProjectColors(requireContext()).getList()) {
                pair -> onColorPicked(pair)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onColorPicked(colorPair: Pair<String, Int>) {
        setFragmentResult(REQUEST_KEY_COLOR, bundleOf(BUNDLE_KEY_COLORTITLE to colorPair.first,
            BUNDLE_KEY_COLORID to colorPair.second)
        )
        dismiss()
    }

    companion object {
        const val REQUEST_KEY_COLOR = "REQUEST_KEY_PROJECT"
        const val BUNDLE_KEY_COLORID = "BUNDLE_KEY_ID"
        const val BUNDLE_KEY_COLORTITLE = "BUNDLE_KEY_TITLE"
    }
}