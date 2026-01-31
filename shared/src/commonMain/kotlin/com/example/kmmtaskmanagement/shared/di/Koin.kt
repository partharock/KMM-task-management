package com.example.kmmtaskmanagement.shared.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(appModule(), platformModule())
}

// iOS entry point
fun initKoin() = initKoin {}
