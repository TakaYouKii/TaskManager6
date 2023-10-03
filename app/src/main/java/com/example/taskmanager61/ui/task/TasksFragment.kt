package com.example.taskmanager61.ui.task

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanager61.R
import com.example.taskmanager61.databinding.FragmentTasksBinding
import com.example.taskmanager61.model.Task
import com.example.taskmanager61.utils.Constant.KEY_ADDT_TO_TF
import com.example.taskmanager61.utils.Constant.KEY_ADD_TASK
import com.example.taskmanager61.utils.Constant.KEY_BUNDLE_TASK
import com.example.taskmanager61.utils.Constant.KEY_TF_TO_ADDT
import com.example.taskmanager61.utils.Constant.KEY_UPDATE_TASK
import com.example.taskmanager61.viewmodel.TasksViewModel
import java.util.zip.Inflater


class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    lateinit var vm: TasksViewModel
    private val adapter = TaskAdapter(
        this::onDoneClick,
        this::onLongClick,
        this::onTaskClick,
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(layoutInflater)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(requireActivity())[TasksViewModel::class.java]
        getData()
        initView()
        initListener()
        initMenu()

    }

    private fun initMenu() {
        val menu: MenuHost = requireActivity()
        menu.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.task_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.checked -> {
                        vm.doSortedList(TaskStatus.DONE)
                        return true
                    }

                    R.id.not_checked -> {
                        vm.doSortedList(TaskStatus.NOT_DONE)
                        return true
                    }

                    R.id.all -> {
                        vm.doSortedList(TaskStatus.ALL)
                        return true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initListener() {
        binding.btnAddTask.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }
    }

    private fun initView() {
        vm.liveData.observe(viewLifecycleOwner){list ->
            adapter.addTasks(list)
            binding.rvTasks.adapter = adapter
        }
    }

    private fun getData() {
        setFragmentResultListener(KEY_ADDT_TO_TF){_, bundle ->
            bundle.getString(KEY_ADD_TASK)?.let {vm.addTask(it) }
            bundle.getSerializable(KEY_UPDATE_TASK)?.let { vm.updateTask(it as Task) }
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
        //findNavController().popBackStack()
        setFragmentResult(KEY_TF_TO_ADDT, bundleOf(KEY_BUNDLE_TASK to task))
        findNavController().navigate(R.id.addTaskFragment)
    }

    private fun onDoneClick(task: Task){
        vm.setTaskDone(task)
    }

}