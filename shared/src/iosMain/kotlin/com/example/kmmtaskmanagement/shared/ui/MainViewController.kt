package com.example.kmmtaskmanagement.shared.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.kmmtaskmanagement.shared.presentation.DefaultRootComponent
import com.example.kmmtaskmanagement.shared.presentation.RootComponent
import platform.UIKit.UIViewController

fun MainViewController(root: RootComponent): UIViewController = ComposeUIViewController {
    App(root)
}

object RootComponentFactory {
    fun create(): RootComponent {
        return DefaultRootComponent(
            componentContext = DefaultComponentContext(LifecycleRegistry())
        )
    }
}
