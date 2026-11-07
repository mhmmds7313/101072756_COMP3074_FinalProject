package com.example.studyflow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyflow.data.Task
import com.example.studyflow.data.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {
    val tasks: Flow<List<Task>> = taskDao.getAllTasks()

    fun addTask(title: String, course: String, dueDate: String, priority: String) {
        viewModelScope.launch {
            taskDao.insert(Task(title = title, course = course, dueDate = dueDate, priority = priority))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }
}
