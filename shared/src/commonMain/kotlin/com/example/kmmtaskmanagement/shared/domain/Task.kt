package com.example.kmmtaskmanagement.shared.domain

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val updatedAt: Long
)
