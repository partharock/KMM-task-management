package com.example.kmmtaskmanagement.shared.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kmmtaskmanagement.shared.domain.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val updatedAt: Long
)

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    updatedAt = updatedAt
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    updatedAt = updatedAt
)
