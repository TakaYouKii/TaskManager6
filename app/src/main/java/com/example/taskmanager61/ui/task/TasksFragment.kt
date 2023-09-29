package com.example.taskmanager61.ui.task

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanager61.R
import com.example.taskmanager61.databinding.FragmentTasksBinding
import com.example.taskmanager61.model.Task
import com.example.taskmanager61.viewmodel.TasksViewModel


class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    lateinit var vm: TasksViewModel
    val adapter = TaskAdapter(
        this::onLongClick,
        this::onTaskClick,
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(layoutInflater)
        vm = ViewModelProvider(requireActivity())[TasksViewModel::class.java]

        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_items,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Обновление выбранного элемента в ViewModel
                vm.selectedSpinnerItem.value = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Обработка события, когда ничего не выбрано
            }
        }

        vm.selectedSpinnerItem.observe(viewLifecycleOwner, Observer { selectedItem ->

            if (selectedItem == "Все") {
                vm.list.value
            } else if (selectedItem == "Завершенные") {
                vm.completedTasks()
            } else if (selectedItem == "Незавершенные") {
                vm.unfinishedTasks()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnAddTask.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }
        binding.rvTasks.adapter = adapter

        vm.list.observe(viewLifecycleOwner, Observer {tasks ->
            tasks?.let {
                adapter.addTasks(it)
            }
        })

        if (arguments != null) {
            val updatedTask = arguments?.getSerializable("updatedTask") as Task?
            if (updatedTask != null) {
                Log.d("ololo", "UT: ${updatedTask.text}")
            }
            if (updatedTask != null) {
                vm.updateTask(updatedTask)
            } else {
                val text = arguments?.getString("task")
                if (text != null) {
                    val data = Task(-1, false, text)
                    vm.addTask(data)
                }
            }
        }

    }
    private fun onLongClick(task: Task): Boolean {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle(getString(R.string.delete))
        builder.setMessage(getString(R.string.delete_message))
        builder.setNeutralButton(getString(R.string.cansel)) { _, _ ->
        }
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            vm.removeTask(task)
        }
        builder.show()
        return true
    }

    private fun onTaskClick(task: Task) {
        findNavController().navigate(R.id.addTaskFragment, bundleOf("task" to task))
    }

}