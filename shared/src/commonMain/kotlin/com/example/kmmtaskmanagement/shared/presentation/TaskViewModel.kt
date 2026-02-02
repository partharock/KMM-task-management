package com.example.kmmtaskmanagement.shared.presentation

import com.example.kmmtaskmanagement.shared.domain.Task
import com.example.kmmtaskmanagement.shared.domain.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskViewModel(
    private val repo: TaskRepository
) {
    val tasks: Flow<List<Task>> = repo.getAllTasks()

    suspend fun toggleTask(task: Task) {
        repo.toggleTaskCompletion(task.id, !task.isCompleted)
    }

    suspend fun save(task: Task) {
        if (task.id.isEmpty()) {
            repo.createTask(task.title, task.description)
        } else {
            repo.updateTask(task)
        }
    }

    suspend fun delete(taskId: String) {
        repo.deleteTask(taskId)
    }
}
