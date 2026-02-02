package com.example.kmmtaskmanagement.shared.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.ConstructedBy

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

// Ensure this is EXACTLY like this
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>
