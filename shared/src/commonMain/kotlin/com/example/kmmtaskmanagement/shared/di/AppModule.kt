package com.example.kmmtaskmanagement.shared.di

import com.arkivanov.decompose.ComponentContext
import com.example.kmmtaskmanagement.shared.data.TaskRepositoryImpl
import com.example.kmmtaskmanagement.shared.domain.TaskRepository
import com.example.kmmtaskmanagement.shared.presentation.*
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule() = module {
    single<TaskRepository> { TaskRepositoryImpl(get()) }

    // Presentation Components
    factory<TaskListComponent> { (context: ComponentContext, onSelected: (String) -> Unit, onCreate: () -> Unit) ->
        DefaultTaskListComponent(context, get(), onSelected, onCreate)
    }

    factory<TaskEditComponent> { (context: ComponentContext, taskId: String?, onFinished: () -> Unit) ->
        DefaultTaskEditComponent(context, get(), taskId, onFinished)
    }
}

expect fun platformModule(): Module
