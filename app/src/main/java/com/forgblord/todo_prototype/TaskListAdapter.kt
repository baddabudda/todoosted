package com.forgblord.todo_prototype

import android.icu.text.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.databinding.ItemTaskBinding
import java.util.Date
import java.util.UUID

class TaskListAdapter (
    private val tasks: MutableList<Task>,
    private val listener: OnItemCheckedListener,
): RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    private var onBind: Boolean = true
    inner class TaskViewHolder (
        private val binding: ItemTaskBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.apply {
                checkBox.isChecked = task.completed
                taskTitle.text = task.title
                taskDate.visibility = View.GONE

                if (task.date != null) {
//                    Log.d("TASKLIST ADAPTER", "GOT DATED TASK: ${task.title} WITH DATE ${task.date}")
                    taskDate.text = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(task.date)
                    taskDate.visibility = View.VISIBLE
                }

                taskItemViewgroup.setOnClickListener {
                    Toast.makeText(root.context, "${task.title} selected!", Toast.LENGTH_SHORT)
                        .show()
                }

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked)
//                        listener.onItemCheck(bindingAdapterPosition)
                        removeOnceCompleted(bindingAdapterPosition, task.id)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        for (task in tasks) {
            Log.d("TASKLIST ADAPTER", "${task.title}")
        }
        onBind = true
        holder.bind(task)
        onBind = false
    }

    interface OnItemCheckedListener {
        fun onItemCheck(id: UUID)
    }

    fun removeOnceCompleted(position: Int, id: UUID) {
        if (!onBind) {
            tasks.removeAt(tasks.indexOf(tasks.find{it.id == id}))
            listener.onItemCheck(id)
            notifyItemRemoved(position)
//            notifyItemRangeChanged(position, tasks.size)

        }
//        notifyItemRangeChanged(position, tasks.size)
    }

}