package com.example.kmmtaskmanagement.shared.ui

import android.R.transition.fade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.example.kmmtaskmanagement.shared.presentation.RootComponent
import com.example.kmmtaskmanagement.shared.ui.theme.AppTheme
import org.koin.compose.KoinContext

@Composable
fun App(root: RootComponent) {
    KoinContext {
        AppTheme {
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
}
