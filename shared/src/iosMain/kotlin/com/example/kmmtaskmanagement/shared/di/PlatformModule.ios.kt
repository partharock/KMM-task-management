// PlatformModule.ios.kt
package com.example.kmmtaskmanagement.shared.di

import org.koin.dsl.module
import com.example.kmmtaskmanagement.shared.data.repository.TaskRepository

actual fun platformModule() = module {
    single<TaskRepository> { TaskRepository() }
}