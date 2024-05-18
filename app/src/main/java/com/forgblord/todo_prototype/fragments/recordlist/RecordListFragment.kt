package com.forgblord.todo_prototype.fragments.recordlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.viewmodels.RecordListViewModel
import com.forgblord.todo_prototype.databinding.FragmentRecodlistBinding
import com.forgblord.todo_prototype.databinding.FragmentTrackBinding
import com.forgblord.todo_prototype.fragments.record_detail.RecordDetailFragment.Companion.recordKey
import com.forgblord.todo_prototype.fragments.recordlist.adapter.RecordListAdapter
import kotlinx.coroutines.launch

class RecordListFragment: Fragment() {
    private var _binding: FragmentRecodlistBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val recordListViewModel: RecordListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecodlistBinding.inflate(inflater, container, false)
        binding.rvRecords.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recordListViewModel.recordList.collect { list ->
                    binding.rvRecords.adapter = RecordListAdapter(list) {
                        recordId -> findNavController().navigate(R.id.action_recordList_to_recordDetail,
                            bundleOf(recordKey to recordId))
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}