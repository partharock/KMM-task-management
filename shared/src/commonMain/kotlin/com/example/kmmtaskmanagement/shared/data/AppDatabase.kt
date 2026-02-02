package com.example.kmmtaskmanagement.shared.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>
