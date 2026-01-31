package com.example.kmmtaskmanagement.shared.data

import java.util.UUID

actual fun generateUUID(): String = UUID.randomUUID().toString()
actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()
