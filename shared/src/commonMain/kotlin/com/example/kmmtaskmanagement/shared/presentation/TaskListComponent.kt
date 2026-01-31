package com.example.kmmtaskmanagement.shared.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.kmmtaskmanagement.shared.domain.Task
import com.example.kmmtaskmanagement.shared.domain.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface TaskListComponent {
    val model: Value<Model>

    fun onTaskClicked(task: Task)
    fun onCreateTaskClicked()
    fun onTaskCompletionToggled(task: Task, isCompleted: Boolean)
    fun onTaskDeleted(task: Task)

    data class Model(
        val tasks: List<Task> = emptyList(),
        val isLoading: Boolean = false
    )
}

class DefaultTaskListComponent(
    componentContext: ComponentContext,
    private val onTaskSelected: (String) -> Unit,
    private val onTaskCreate: () -> Unit
) : TaskListComponent, ComponentContext by componentContext, KoinComponent {

    private val repository: TaskRepository by inject()
    // In a real app, use a lifecycle-aware scope or value-based reactivity correctly
    // For simplicity, using a CoroutineScope bound to the component
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _model = MutableValue(TaskListComponent.Model(isLoading = true))
    override val model: Value<TaskListComponent.Model> = _model

    init {
        scope.launch {
            try {
                repository.getAllTasks().collectLatest { tasks ->
                    _model.value = TaskListComponent.Model(tasks = tasks, isLoading = false)
                }
            } catch (e: Exception) {
                println("Error loading tasks: ${e.message}")
                _model.value = TaskListComponent.Model(isLoading = false)
            }
        }
    }

    override fun onTaskClicked(task: Task) {
        onTaskSelected(task.id)
    }

    override fun onCreateTaskClicked() {
        onTaskCreate()
    }

    override fun onTaskCompletionToggled(task: Task, isCompleted: Boolean) {
        scope.launch {
            try {
                repository.toggleTaskCompletion(task.id, isCompleted)
            } catch (e: Exception) {
                // Should show error to user
                println("Error toggling task completion: ${e.message}")
            }
        }
    }

    override fun onTaskDeleted(task: Task) {
         scope.launch {
             try {
                repository.deleteTask(task.id)
             } catch (e: Exception) {
                 println("Error deleting task: ${e.message}")
             }
         }
    }
}
