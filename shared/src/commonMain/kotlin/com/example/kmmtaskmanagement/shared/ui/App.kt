package com.example.kmmtaskmanagement.shared.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.example.kmmtaskmanagement.shared.presentation.RootComponent

@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Children(
                stack = root.childStack,
                animation = stackAnimation(fade())
            ) {
                when (val child = it.instance) {
                    is RootComponent.Child.TaskList -> TaskListScreen(child.component)
                    is RootComponent.Child.TaskEdit -> TaskEditScreen(child.component)
                }
            }
        }
    }
}
