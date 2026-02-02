# KMM Task Management

A cross-platform task management application built with **Kotlin Multiplatform (KMP)**, demonstrating a robust architecture shared between Android and iOS.

## 🛠 Tech Stack

*   **Language**: Kotlin (100% Shared Logic & UI)
*   **UI Framework**: [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
*   **Architecture**: Clean Architecture (Domain, Data, Presentation)
*   **Navigation**: [Decompose](https://arkivanov.github.io/Decompose/) (Lifecycle-aware, component-based)
*   **Dependency Injection**: [Koin](https://insert-koin.io/) (Constructor Injection)
*   **Persistence**: [Room for KMP](https://developer.android.com/kotlin/multiplatform/room)
*   **Concurrency**: Kotlin Coroutines & Flow (Explicit Threading Management)

## 💾 Persistence Choice: Room KMP

**Room** was chosen over SQLDelight for the following reasons:
1.  **Familiar API**: Provides a high-level abstraction (DAOs, Entities) that is standard in modern Android development, accelerating cross-platform migration.
2.  **Reactive Support**: Native support for `Flow` ensures the UI updates automatically whenever data changes in the database.
3.  **Type Safety**: KSP-based code generation ensures compile-time safety for SQL queries and object mapping.

## 🏗 Architecture & Design Patterns

### 1. Clean Architecture
The project is strictly divided into three layers to ensure maintainability and testability:
*   **Domain**: Contains pure Kotlin entities (`Task`) and repository interfaces.
*   **Data**: Implements the repository using Room KMP. It handles data mapping and ensures all I/O is performed off-thread.
*   **Presentation**: Uses **Decompose Components** to manage state and navigation logic. Platform-specific UIs merely render the state provided by these shared components.

### 2. Dependency Injection (Koin)
*   **No Manual DI**: All dependencies, including Repositories and Decompose Components, are registered in Koin modules (`AppModule`, `PlatformModule`).
*   **Constructor Injection**: Follows senior-level best practices by avoiding the Service Locator pattern inside logic classes, using constructor injection instead.

### 3. Navigation (Decompose)
*   Navigation state is managed by the `RootComponent`, which uses a `ChildStack` to handle screen transitions.
*   Back-button handling and component lifecycles are unified across Android and iOS.

### 4. Threading Strategy (Strict Requirement)
*   **Main Thread Protection**: No database or blocking I/O operations are allowed on the main thread.
*   **Explicit Dispatchers**: All repository methods are wrapped in `withContext(Dispatchers.IO)` (shared) to ensure background execution.
*   **Reactive Streams**: Database queries return `Flow<List<Task>>` and use `.flowOn(Dispatchers.IO)` to protect downstream observers.

## 🚀 Running the Project

### Prerequisites
*   **JDK 17**
*   **Android Studio** (Koala or newer)
*   **Xcode** & **CocoaPods** (for iOS build)

### Android
1.  Select the `androidApp` run configuration.
2.  Click **Run**.

### iOS
1.  Build the shared framework: `./gradlew :shared:assembleDebug`
2.  Install Pods: `cd iosApp && pod install`
3.  Open `iosApp.xcworkspace` in Xcode.
4.  Run on an iOS Simulator.

*Note: The project uses a build directory workaround (`/tmp/kmm_build/`) to avoid KSP issues with spaces in file paths. Ensure Xcode "Framework Search Paths" point to `/tmp/kmm_build/shared/cocoapods/framework` if linking manually.*

## ✅ Verification Features
*   **Persistence**: Tasks are saved locally and persist across app restarts.
*   **Sorting**: Tasks retain their insertion order regardless of updates.
*   **UI Sync**: Strikethrough visual for completed tasks.
*   **Timestamps**: Displays the exact last-updated time (including seconds) using platform-native date formatters.
