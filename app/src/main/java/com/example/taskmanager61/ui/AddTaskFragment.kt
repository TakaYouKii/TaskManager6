package com.example.taskmanager61.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanager61.R
import com.example.taskmanager61.databinding.FragmentAddTaskBinding
import com.example.taskmanager61.model.Task
import com.example.taskmanager61.ui.task.TasksFragment
import com.example.taskmanager61.viewmodel.TasksViewModel


class AddTaskFragment : Fragment() {

    lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            if(arguments==null) {
                addTask()
            } else{
                updateTask()
            }
    }

    fun addTask(){
        binding.btnAddTask.setOnClickListener {
            val text = binding.etAddTask.text.toString()
            if (text.equals(null)){
                Log.d("ololo", "add-null: $text")
            }else{
                Log.d("ololo", "add-full: $text")
                findNavController().navigate(R.id.tasksFragment, bundleOf("task" to text))
            }

        }
    }


    private fun updateTask(){
        val task=arguments?.getSerializable("task") as Task
        binding.etAddTask.setText(task.text)
        binding.btnAddTask.text="Update task"
        binding.btnAddTask.setOnClickListener {
            val updatedTask= task.copy(
                text = binding.etAddTask.text.toString(),
            )
            findNavController().navigate(R.id.tasksFragment, bundleOf("updatedTask" to updatedTask))
        }
    }
}