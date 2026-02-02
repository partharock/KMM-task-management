package com.example.kmmtaskmanagement.shared.data.repository

import com.example.kmmtaskmanagement.shared.domain.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual class TaskRepository {

    private val tasks = MutableStateFlow<List<Task>>(emptyList())

    actual fun observeTasks(): Flow<List<Task>> =
        tasks.asStateFlow()

    actual suspend fun getTaskById(id: String): Task? =
        tasks.value.find { it.id == id }

    actual suspend fun upsertTask(task: Task) {
        val current = tasks.value.toMutableList()
        val index = current.indexOfFirst { it.id == task.id }
        if (index >= 0) {
            current[index] = task
        } else {
            current.add(task)
        }
        tasks.value = current
    }

    actual suspend fun setTaskCompleted(
        taskId: String,
        completed: Boolean,
        updatedAt: Long
    ) {
        tasks.value = tasks.value.map {
            if (it.id == taskId)
                it.copy(isCompleted = completed, updatedAt = updatedAt)
            else it
        }
    }

    actual suspend fun deleteTask(taskId: String) {
        tasks.value = tasks.value.filterNot { it.id == taskId }
    }
}