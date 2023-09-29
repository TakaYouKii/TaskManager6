package com.example.taskmanager61.ui.task

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager61.databinding.TaskItemsBinding
import com.example.taskmanager61.model.Task
import com.example.taskmanager61.model.Task as task

class TaskAdapter(
    val onLongClick: (task: Task) -> Boolean,
    private var onClick: (task: Task) -> Unit,
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    private var list = mutableListOf<task>()

    @SuppressLint("NotifyDataSetChanged")
    fun addTasks(tasks: MutableList<task>){
        this.list = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(TaskItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


    inner class TaskViewHolder(private val binding: TaskItemsBinding) : ViewHolder(binding.root) {
        private val checkBox: CheckBox = binding.chbCompleted

        init {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val spannableText = SpannableString(binding.tvTask.text.toString())
                if (isChecked) {
                    spannableText.setSpan(StrikethroughSpan(), 0, spannableText.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
                } else {
                    for (span in spannableText) {
                        spannableText.removeSpan(span)
                    }
                }
                binding.tvTask.text = spannableText
            }
        }

        fun bind(task: Task) {
            task.id = adapterPosition
            binding.tvTask.text = task.text
            itemView.setOnLongClickListener {
                onLongClick(list[adapterPosition])
            }
            itemView.setOnClickListener{
                onClick(task)
            }
        }
    }

}
