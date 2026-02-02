package com.example.kmmtaskmanagement.shared.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.kmmtaskmanagement.shared.data.AppDatabase
import com.example.kmmtaskmanagement.shared.data.AppDatabaseConstructor
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun platformModule() = module {
    single<AppDatabase> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        val dbFilePath = documentDirectory?.path + "/tasks.db"
        
        Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
            factory = { AppDatabaseConstructor.initialize() }
        )
        .setDriver(BundledSQLiteDriver())
        .build()
    }
    
    single { get<AppDatabase>().taskDao() }
}
