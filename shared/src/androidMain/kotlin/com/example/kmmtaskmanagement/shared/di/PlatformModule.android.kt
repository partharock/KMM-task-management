package com.example.kmmtaskmanagement.shared.di

import androidx.room.Room
import com.example.kmmtaskmanagement.shared.data.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AppDatabase> {
        val context = androidContext()
        val dbFile = context.getDatabasePath("tasks.db")
        Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).build()
    }
    single { get<AppDatabase>().taskDao() }
}
