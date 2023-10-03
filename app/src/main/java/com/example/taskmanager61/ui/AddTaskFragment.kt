package com.example.taskmanager61.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.taskmanager61.databinding.FragmentAddTaskBinding
import com.example.taskmanager61.model.Task
import com.example.taskmanager61.utils.Constant.KEY_ADDT_TO_TF
import com.example.taskmanager61.utils.Constant.KEY_ADD_TASK
import com.example.taskmanager61.utils.Constant.KEY_BUNDLE_TASK
import com.example.taskmanager61.utils.Constant.KEY_TF_TO_ADDT
import com.example.taskmanager61.utils.Constant.KEY_UPDATE_TASK


class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private var _task: Task? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        getData()
    }

    private fun getData() {
        setFragmentResultListener(KEY_TF_TO_ADDT){_,bundle ->
            bundle.getSerializable(KEY_BUNDLE_TASK)?.let { task ->
                _task = task as Task
                loadData()
            }

        }
    }

    private fun loadData() {
        _task?.let { task->
            binding.btnAddTask.text = "Update"
            binding.chbState.visibility = View.VISIBLE
            binding.chbState.isChecked= task.checked
            binding.etAddTask.setText(task.text)
        }
    }

    private fun initListener() {
        binding.btnAddTask.setOnClickListener {
            if(_task == null){
                addTask()
            }else{
                _task!!.text = binding.etAddTask.text.toString()
                _task!!.checked = binding.chbState.isChecked
                updateTask()
            }
        }
    }

    private fun addTask() {
        setFragmentResult(KEY_ADDT_TO_TF, bundleOf(KEY_ADD_TASK to binding.etAddTask.text.toString()))
        findNavController().navigateUp()
    }


    private fun updateTask(){
        setFragmentResult(KEY_ADDT_TO_TF, bundleOf(KEY_UPDATE_TASK to _task))
        findNavController().navigateUp()
    }
}