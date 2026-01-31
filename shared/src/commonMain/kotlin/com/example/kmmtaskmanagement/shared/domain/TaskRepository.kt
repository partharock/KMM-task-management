package com.example.kmmtaskmanagement.shared.domain

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: String): Task?
    suspend fun createTask(title: String, description: String?)
    suspend fun updateTask(task: Task)
    suspend fun toggleTaskCompletion(taskId: String, isCompleted: Boolean)
    suspend fun deleteTask(taskId: String)
}
