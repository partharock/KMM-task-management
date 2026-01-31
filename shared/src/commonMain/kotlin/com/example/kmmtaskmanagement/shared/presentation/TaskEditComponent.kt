package com.example.kmmtaskmanagement.shared.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.kmmtaskmanagement.shared.domain.Task
import com.example.kmmtaskmanagement.shared.domain.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface TaskEditComponent {
    val model: Value<Model>

    fun onTitleChanged(title: String)
    fun onDescriptionChanged(description: String)
    fun onSaveClicked()
    fun onBackClicked()

    data class Model(
        val title: String = "",
        val description: String = "",
        val isEditing: Boolean = false // true if editing existing task
    )
}

class DefaultTaskEditComponent(
    componentContext: ComponentContext,
    private val taskId: String?,
    private val onFinished: () -> Unit
) : TaskEditComponent, ComponentContext by componentContext, KoinComponent {

    private val repository: TaskRepository by inject()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _model = MutableValue(TaskEditComponent.Model(isEditing = taskId != null))
    override val model: Value<TaskEditComponent.Model> = _model

    private var currentTitle = ""
    private var currentDescription = ""

    init {
        taskId?.let { id ->
            scope.launch {
                try {
                    val task = repository.getTaskById(id)
                    task?.let {
                        currentTitle = it.title
                        currentDescription = it.description ?: ""
                        updateModel()
                    }
                } catch (e: Exception) {
                    println("Error loading task details: ${e.message}")
                }
            }
        }
    }

    private fun updateModel() {
        _model.value = _model.value.copy(
            title = currentTitle,
            description = currentDescription
        )
    }

    override fun onTitleChanged(title: String) {
        currentTitle = title
        updateModel()
    }

    override fun onDescriptionChanged(description: String) {
        currentDescription = description
        updateModel()
    }

    override fun onSaveClicked() {
        if (currentTitle.isBlank()) return

        scope.launch {
            try {
                if (taskId != null) {
                    val originalTask = repository.getTaskById(taskId)
                    originalTask?.let {
                        repository.updateTask(it.copy(title = currentTitle, description = currentDescription))
                    }
                } else {
                    repository.createTask(currentTitle, currentDescription)
                }
                onFinished()
            } catch (e: Exception) {
                println("Error saving task: ${e.message}")
            }
        }
    }

    override fun onBackClicked() {
        onFinished()
    }
}
