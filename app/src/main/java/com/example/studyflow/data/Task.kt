package com.example.studyflow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val course: String,
    val dueDate: String,
    val priority: String // "HIGH", "MEDIUM", "LOW"
)
