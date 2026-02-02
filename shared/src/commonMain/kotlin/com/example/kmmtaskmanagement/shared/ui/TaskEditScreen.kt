package com.example.kmmtaskmanagement.shared.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.kmmtaskmanagement.shared.presentation.TaskEditComponent

@Composable
fun TaskEditScreen(component: TaskEditComponent) {
    val model by component.model.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (model.isEditing) "Edit Task" else "New Task") },
                navigationIcon = {
                    IconButton(onClick = component::onBackClicked) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            TextField(
                value = model.title,
                onValueChange = component::onTitleChanged,
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                value = model.description,
                onValueChange = component::onDescriptionChanged,
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = component::onSaveClicked,
                modifier = Modifier.fillMaxWidth(),
                enabled = model.title.isNotBlank()
            ) {
                Text("Save")
            }
        }
    }
}
