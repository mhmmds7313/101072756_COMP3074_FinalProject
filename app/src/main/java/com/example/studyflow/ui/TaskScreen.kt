package com.example.studyflow.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studyflow.data.Task

@Composable
fun StudyFlowApp(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(initialValue = emptyList())
    
    TaskScreen(
        tasks = tasks,
        onAddTask = viewModel::addTask,
        onDeleteTask = viewModel::deleteTask
    )
}

@Composable
fun TaskScreen(
    tasks: List<Task>,
    onAddTask: (String, String, String, String) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var course by rememberSaveable { mutableStateOf("") }
    var dueDate by rememberSaveable { mutableStateOf("") }
    var priority by rememberSaveable { mutableStateOf("MEDIUM") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("StudyFlow Dashboard", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Task Title") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = course, onValueChange = { course = it }, label = { Text("Course (e.g. COMP3074)") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = dueDate, onValueChange = { dueDate = it }, label = { Text("Due Date (e.g. Oct 25)") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Priority: ")
            listOf("HIGH", "MEDIUM", "LOW").forEach { p ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 4.dp)) {
                    RadioButton(selected = priority == p, onClick = { priority = p })
                    Text(p)
                }
            }
        }
        
        Button(
            onClick = {
                if (title.isNotBlank() && course.isNotBlank()) {
                    onAddTask(title, course, dueDate, priority)
                    title = ""
                    course = ""
                    dueDate = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(Modifier.height(16.dp))
        Text("Upcoming Tasks", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        if (tasks.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                Text("No tasks yet. Add one above!", color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tasks, key = { it.id }) { task ->
                    TaskRow(task, onDeleteTask)
                }
            }
        }
    }
}

@Composable
fun TaskRow(task: Task, onDelete: (Task) -> Unit) {
    val priorityColor = when (task.priority) {
        "HIGH" -> Color.Red
        "LOW" -> Color.Green
        else -> Color(0xFFFFA500)
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("${task.course} | Due: ${task.dueDate}", style = MaterialTheme.typography.bodyMedium)
                Text("Priority: ${task.priority}", color = priorityColor, style = MaterialTheme.typography.bodySmall)
            }
            TextButton(onClick = { onDelete(task) }) {
                Text("Done", color = Color.Blue)
            }
        }
    }
}
