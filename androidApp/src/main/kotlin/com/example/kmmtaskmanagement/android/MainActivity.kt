package com.example.kmmtaskmanagement.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.example.kmmtaskmanagement.shared.di.initKoin
import com.example.kmmtaskmanagement.shared.presentation.DefaultRootComponent
//import com.example.kmmtaskmanagement.shared.presentation.DefaultRootComponent
import com.example.kmmtaskmanagement.shared.ui.App
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init Koin if not already started (In a real app, do this in Application class)
        // For simplicity in this structure:
        try {
            initKoin {
                androidLogger()
                androidContext(applicationContext)
            }
        } catch (e: Exception) {
            // Already initialized
        }

        val root = DefaultRootComponent(
            componentContext = defaultComponentContext()
        )

        setContent {
            App(root)
        }
    }
}
