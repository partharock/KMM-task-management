package com.example.kmmtaskmanagement.shared.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object AppCoroutineScope {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}