plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    jvmToolchain(11)
    
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    
    // Check if we are running on a Mac
    val isMac = System.getProperty("os.name")?.contains("Mac", ignoreCase = true) == true
    
    if (isMac) {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "Shared"
                isStatic = true
            }
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.components.uiToolingPreview)
            implementation(libs.material.icons.core)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            
            // Room
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
        }
        
        androidMain.dependencies {
            implementation(libs.androidx.sqlite)
            implementation(libs.androidx.sqlite.framework)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.kotlinx.coroutines.android)
        }
        
        if (isMac) {
             iosMain.dependencies {
                // iOS specific dependencies if any
             }
        }
    }
}

android {
    namespace = "com.example.kmmtaskmanagement.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

room {
    // Workaround: Project path has spaces, which breaks KSP. Use a temp path without spaces.
    schemaDirectory("/tmp/kmm_schemas")
}

dependencies {
    // Room Compiler - KSP
    add("kspCommonMainMetadata", libs.androidx.room.compiler)
    add("kspAndroid", libs.androidx.room.compiler)
    
    // Only add iOS KSP dependencies if we are on Mac active targets
    if (System.getProperty("os.name")?.contains("Mac", ignoreCase = true) == true) {
         add("kspIosX64", libs.androidx.room.compiler)
         add("kspIosArm64", libs.androidx.room.compiler)
         add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    }
}
