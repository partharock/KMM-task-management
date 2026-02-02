package com.example.kmmtaskmanagement.shared.presentation

import com.example.kmmtaskmanagement.shared.domain.Task
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskViewModelWrapper(
    private val viewModel: TaskViewModel
) {

    private val scope = MainScope()

    fun observeTasks(onChange: (List<Task>) -> Unit) {
        scope.launch {
            viewModel.tasks.collect { tasks ->
                onChange(tasks)
            }
        }
    }
}