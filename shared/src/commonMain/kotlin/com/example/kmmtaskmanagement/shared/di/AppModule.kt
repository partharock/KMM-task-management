package com.example.kmmtaskmanagement.shared.di

import com.example.kmmtaskmanagement.shared.data.TaskRepositoryImpl
import com.example.kmmtaskmanagement.shared.domain.TaskRepository
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule() = module {
    single<TaskRepository> { TaskRepositoryImpl(get()) }
}

expect fun platformModule(): Module
