package com.example.kmmtaskmanagement.shared.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.kmmtaskmanagement.shared.data.AppDatabase
import com.example.kmmtaskmanagement.shared.data.instantiateImpl
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual fun platformModule() = module {
    single<AppDatabase> {
        val dbFilePath = NSHomeDirectory() + "/tasks.db"
        
        // Note: Generic type arguments might need to be explicit or different based on Room KMP version
        val builder = Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
            factory = { AppDatabase::class.instantiateImpl() } // This requires the Room compiler to generate the implementation
        )
        // Add the driver
        builder.setDriver(BundledSQLiteDriver()) // Important for iOS to use the bundled SQLite
        builder.build()
    }
    single { get<AppDatabase>().taskDao() }
}
