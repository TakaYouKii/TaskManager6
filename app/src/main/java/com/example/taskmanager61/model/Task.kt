package com.example.taskmanager61.model

import java.io.Serializable

data class Task(
    var id: Int,
    var checked: Boolean = false,
    var text: String
):Serializable
