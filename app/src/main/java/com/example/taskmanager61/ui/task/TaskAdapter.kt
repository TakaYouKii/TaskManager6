package com.example.taskmanager61.ui.task

import android.annotation.SuppressLint
import android.graphics.Color
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
    val onDoneClick: (task: Task)->Unit,
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

        fun bind(task: Task) {
            task.id = adapterPosition
            binding.tvTask.text = task.text
            binding.chbCompleted.isChecked = task.checked
            if (binding.chbCompleted.isChecked){
                binding.tvTask.setTextColor(Color.GRAY)
            }
            binding.chbCompleted.setOnClickListener {
                onDoneClick(task)
            }
            itemView.setOnLongClickListener {
                onLongClick(list[adapterPosition])
            }
            itemView.setOnClickListener{
                onClick(task)
            }
        }
    }

}
