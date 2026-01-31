package com.example.kmmtaskmanagement.shared.data

import com.example.kmmtaskmanagement.shared.domain.Task
import com.example.kmmtaskmanagement.shared.domain.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { entities -> entities.map { it.toDomain() } }
            .flowOn(ioDispatcher)
    }

    override suspend fun getTaskById(id: String): Task? {
        return withContext(ioDispatcher) {
            taskDao.getTaskById(id)?.toDomain()
        }
    }

    override suspend fun createTask(title: String, description: String?) {
        withContext(ioDispatcher) {
            val task = Task(
                id = generateUUID(), // We need a UUID generator
                title = title,
                description = description,
                isCompleted = false,
                updatedAt = getCurrentTimeMillis()
            )
            taskDao.insertTask(task.toEntity())
        }
    }

    override suspend fun updateTask(task: Task) {
        withContext(ioDispatcher) {
            taskDao.updateTask(task.toEntity())
        }
    }

    override suspend fun toggleTaskCompletion(taskId: String, isCompleted: Boolean) {
        withContext(ioDispatcher) {
            val entity = taskDao.getTaskById(taskId)
            entity?.let {
                taskDao.updateTask(it.copy(isCompleted = isCompleted))
            }
        }
    }

    override suspend fun deleteTask(taskId: String) {
        withContext(ioDispatcher) {
            taskDao.deleteTask(taskId)
        }
    }
}

// Utils (normally would be in separate files)
expect fun generateUUID(): String
expect fun getCurrentTimeMillis(): Long
