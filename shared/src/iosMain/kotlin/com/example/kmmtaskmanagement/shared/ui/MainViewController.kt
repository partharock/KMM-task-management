package com.example.kmmtaskmanagement.shared.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.kmmtaskmanagement.shared.presentation.DefaultRootComponent
import com.example.kmmtaskmanagement.shared.presentation.RootComponent

fun MainViewController(root: RootComponent) = ComposeUIViewController {
    App(root)
}

// In a real iOS KMP app using Decompose, we need to handle the Lifecycle manually 
// passing it from the Swift side or creating a RootComponentProvider.
// This is a simplified helper if Swift handles the context creation, 
// OR we can expose a helper to create the root component.
