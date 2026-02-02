package com.example.kmmtaskmanagement.shared.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class TaskList(val component: TaskListComponent) : Child()
        data class TaskEdit(val component: TaskEditComponent) : Child()
    }

    fun onBackClicked()
}

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.TaskList,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.TaskList -> RootComponent.Child.TaskList(
                get {
                    parametersOf(
                        componentContext,
                        { taskId: String -> navigation.push(Config.TaskEdit(taskId)) },
                        { navigation.push(Config.TaskEdit(null)) }
                    )
                }
            )
            is Config.TaskEdit -> RootComponent.Child.TaskEdit(
                get {
                    parametersOf(
                        componentContext,
                        config.taskId,
                        { navigation.pop() }
                    )
                }
            )
        }

    override fun onBackClicked() {
        navigation.pop()
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object TaskList : Config

        @Serializable
        data class TaskEdit(val taskId: String?) : Config
    }
}
