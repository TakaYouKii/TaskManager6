package com.example.taskmanager61.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskmanager61.R
import com.example.taskmanager61.databinding.FragmentTasksBinding
import com.example.taskmanager61.viewmodel.TasksViewModel

class TestFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private lateinit var viewModel: TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(layoutInflater)
        return binding.root
    }

}