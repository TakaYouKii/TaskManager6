package com.example.taskmanager61.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskmanager61.model.Task


class TasksViewModel : ViewModel() {
    private var tasks= mutableListOf<Task>()
    private var _list=MutableLiveData<MutableList<Task>>()
    var list: LiveData<MutableList<Task>> = _list

    val selectedSpinnerItem = MutableLiveData<String>()


    fun addTask(task: Task) {
        tasks.add(task)
        _list.value = tasks
    }

    fun removeTask(task: Task) {
        tasks.remove(task)
        _list.value=tasks
    }

    fun updateTask(updatedTask: Task) {
        tasks.replaceAll { task ->
            when (task.id) {
                updatedTask.id -> {
                    task.text = updatedTask.text
                    task
                }
                else -> task
            }
        }
    }

    fun completedTasks() {
        val completed = tasks
        for(task in completed){
            if(task.checked){
                completed.add(task)
            }
        }
        _list.value = completed
    }

    fun unfinishedTasks(){
        val unfinished = tasks
        for(task in unfinished){
            if(!task.checked){
                unfinished.add(task)
            }
        }
        _list.value = unfinished
    }

}

