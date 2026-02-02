package com.example.kmmtaskmanagement.shared.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.kmmtaskmanagement.shared.presentation.TaskListComponent
import com.example.kmmtaskmanagement.shared.util.formatTimestamp

@Composable
fun TaskListScreen(component: TaskListComponent) {
    val model by component.model.subscribeAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tasks") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = component::onCreateTaskClicked) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        if (model.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(bottom = 80.dp) // Space for FAB
            ) {
                items(
                    items = model.tasks,
                    key = { it.id } // Added key for better performance and scroll state preservation
                ) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { component.onTaskClicked(task) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = { component.onTaskCompletionToggled(task, it) }
                        )
                        Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.h6,
                                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                            )
                            task.description?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.body2,
                                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                                )
                            }
                            Text(
                                text = "Updated: ${formatTimestamp(task.updatedAt)}",
                                style = MaterialTheme.typography.caption,
                                color = Color.Gray,
                                fontSize = 10.sp
                            )
                        }
                        IconButton(onClick = { component.onTaskDeleted(task) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                    Divider() // Added divider for better visual separation in long lists
                }
            }
        }
    }
}
