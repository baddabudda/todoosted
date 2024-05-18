package com.forgblord.todo_prototype.fragments.recordlist.adapter

import android.icu.text.SimpleDateFormat
import com.forgblord.todo_prototype.data.models.TaskRecords
import com.forgblord.todo_prototype.data.models.TimeRecord
import com.forgblord.todo_prototype.databinding.ItemRecordBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.data.models.RecordTask
import com.forgblord.todo_prototype.data.models.Task
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class TaskListViewHolder (
    private val binding: ItemRecordBinding,
//    private val onCheckListener: (task: Task) -> Unit,
//    private val onTrackClicked: (taskId: Int) -> Unit,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(record: TaskRecords) {
        binding.apply {
            taskTitle.text = record.taskTitle
            val elapsedTime = record.record.duration!!.seconds
            val hrs = elapsedTime.inWholeHours.toString().padStart(2, '0')
            val mins = elapsedTime.inWholeMinutes.toString().padStart(2, '0')
            val seconds = elapsedTime.inWholeSeconds.toString().padStart(2, '0')
            tvDuration.text = "$hrs:$mins:$seconds"

            /*if (task.completed) {
                checkBox.isChecked = task.completed
                this.taskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }*/

            /*trackItemViewgroup.setOnClickListener {
                onTaskClicked(task.task_id)
            }*/

            /*checkBox.buttonTintList = ColorStateList.valueOf(priorityToColor(root.context, task.priority))

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                this.taskTitle.paintFlags = if (isChecked) Paint.STRIKE_THRU_TEXT_FLAG else 0
                task.completed = isChecked
                updateList(absoluteAdapterPosition)
                onCheckListener(task)
            }*/

            /*btnStartTrack.setOnClickListener {
                onTrackClicked(taskProject.task.task_id)
            }*/
        }
    }
}

class RecordListAdapter (
    private val taskRecords: List<TaskRecords>,
//    private val onCheckListener: (task: Task) -> Unit,
//    private val onItemClickListener: (id: Int) -> Unit,
): RecyclerView.Adapter<TaskListViewHolder>() {
    private var onBind: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecordBinding.inflate(inflater, parent, false)
        return TaskListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskRecords.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val record = taskRecords[position]
        onBind = true
        holder.bind(record)
        onBind = false
    }

    /*private fun removeOnceCompleted(position: Int) {
        if (!onBind) notifyItemRemoved(position)
    }*/
}