package com.example.kmmtaskmanagement.shared.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.example.kmmtaskmanagement.shared.domain.Task
import com.example.kmmtaskmanagement.shared.domain.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    private val repository: TaskRepository,
    private val onTaskSelected: (String) -> Unit,
    private val onTaskCreate: () -> Unit
) : TaskListComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _model = MutableValue(TaskListComponent.Model(isLoading = true))
    override val model: Value<TaskListComponent.Model> = _model

    init {
        lifecycle.doOnDestroy { scope.cancel() }
        
        scope.launch {
            try {
                repository.getAllTasks().collectLatest { tasks ->
                    _model.value = TaskListComponent.Model(tasks = tasks, isLoading = false)
                }
            } catch (e: Exception) {
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
            repository.toggleTaskCompletion(task.id, isCompleted)
        }
    }

    override fun onTaskDeleted(task: Task) {
         scope.launch {
             repository.deleteTask(task.id)
         }
    }
}
