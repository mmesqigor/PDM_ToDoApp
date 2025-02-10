package com.example.trab1_todolist.domain

data class Task(
    val id: Long,
    val title: String,
    val description: String? = null,
    val startTime: String,
    val endTime: String,
    val isCompleted: Boolean
)

// Fake objects
val taskList = listOf(
    Task(
        1,
        "Do Laundry",
        "Wash and fold clothes",
        "10:00",
        "11:00",
        false
    ),
    Task(
        2,
        "Clean Kitchen",
        "Wash dishes, wipe counters, and mop the floor",
        "11:30",
        "12:30",
        false
    ),
    Task(
        3,
        "Vacuum Living Room",
        "Clean carpets and furniture",
        "13:00",
        "14:00",
        false
    )
)