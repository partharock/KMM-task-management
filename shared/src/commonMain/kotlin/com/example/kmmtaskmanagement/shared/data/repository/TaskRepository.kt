package com.example.kmmtaskmanagement.shared.data.repository

import com.example.kmmtaskmanagement.shared.domain.Task
import kotlinx.coroutines.flow.Flow

expect class TaskRepository {
    fun observeTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: String): Task?
    suspend fun upsertTask(task: Task)
    suspend fun setTaskCompleted(
        taskId: String,
        completed: Boolean,
        updatedAt: Long
    )
    suspend fun deleteTask(taskId: String)
}