package com.example.studyflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyflow.data.AppDatabase
import com.example.studyflow.ui.StudyFlowApp
import com.example.studyflow.ui.TaskViewModel
import com.example.studyflow.ui.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(applicationContext)

        setContent {
            MaterialTheme {
                val viewModel: TaskViewModel = viewModel(
                    factory = TaskViewModelFactory(database.taskDao())
                )
                StudyFlowApp(viewModel)
            }
        }
    }
}
