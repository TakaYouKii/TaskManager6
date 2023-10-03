package com.example.taskmanager61.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskmanager61.model.Task
import com.example.taskmanager61.ui.task.TaskStatus


class TasksViewModel : ViewModel() {
    private var _liveData = MutableLiveData<MutableList<Task>>()
    private val tasks = mutableListOf<Task>()
    private var _sort = TaskStatus.ALL
    val liveData:LiveData<MutableList<Task>>
        get() = _liveData

    fun doSortedList(status: TaskStatus){
        _sort = status
        setDataToShow()
    }

    fun addTask(text:String) {
        tasks.add(
            Task(
                text = text,
                id = tasks.size)
        )
        setDataToShow()
    }

    fun updateTask(task: Task){
        removeTask(task)
        tasks.add(task.id,task)
        setDataToShow()

    }

    private fun setDataToShow() {
        when (_sort){
            TaskStatus.DONE -> showDoneTask()
            TaskStatus.NOT_DONE -> showNotDoneTask()
            else -> showAllTask()
        }
    }

    private fun showAllTask() {
        _liveData.value = tasks
    }

    private fun showNotDoneTask(){
        val sortedList = mutableListOf<Task>()
        tasks.forEach { task ->
            if (!task.checked) sortedList.add(task)
        }
        _liveData.value = sortedList
    }

    private fun showDoneTask() {
        val sortedList = mutableListOf<Task>()
        tasks.forEach { task ->
            if (task.checked) sortedList.add(task)
        }
        _liveData.value = sortedList
    }

    fun removeTask(task: Task) {
        tasks.removeAt(task.id)
        showAllTask()
    }
    fun setTaskDone(task: Task){
        tasks[task.id].checked = true
        showAllTask()
    }
}

