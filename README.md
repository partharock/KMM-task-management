# KMM Task Management

A cross-platform task management application built with **Kotlin Multiplatform (KMP)**, sharing logic and UI between Android and iOS.

## 🛠 Tech Stack

*   **Language**: Kotlin (100%)
*   **UI Framework**: [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (Shared UI)
*   **Architecture**: Clean Architecture (MVI-style presentation)
*   **Navigation**: [Decompose](https://arkivanov.github.io/Decompose/)
*   **Dependency Injection**: [Koin](https://insert-koin.io/)
*   **Persistence**: [Room for KMP](https://developer.android.com/kotlin/multiplatform/room)
*   **Concurrency**: Kotlin Coroutines & Flow

## 💾 Persistence Choice: Room KMP

For this project, **Room** was chosen as the persistence solution over SQLDelight.

**Justification:**
1.  **Familiarity & Adoption**: Room is the standard persistence library for modern Android development. Its KMP version maintains the exact same API (DAOs, Entities, `@Database`), allowing Android developers to transition to KMP without learning a new database query language or toolchain.
2.  **Abstraction Level**: Room provides a higher-level object-mapping abstraction compared to SQLDelight. It handles the boilerplate of cursor-to-object mapping automatically, which accelerates development for standard CRUD applications.
3.  **Modern Integration**: It offers first-class support for Kotlin Coroutines and `Flow`, enabling reactive data updates to the UI with minimal configuration.
4.  **Ecosystem**: Being part of AndroidX, it ensures long-term support and seamless compatibility with other Jetpack libraries.

## 🏗 Architecture

The project follows **Clean Architecture** principles, enforcing separation of concerns:

-   **Domain Layer** (`shared/src/commonMain/.../domain`):
    -   Contains pure business logic and entities.
    -   Defines Repository interfaces.
    -   Platform-agnostic (no Android/iOS dependencies).

-   **Data Layer** (`shared/src/commonMain/.../data`):
    -   Implements Repository interfaces.
    -   Manages the Room database and Data Sources.
    -   Handles data mapping (DTOs <-> Domain Models).

-   **Presentation Layer** (`shared/src/commonMain/.../presentation`):
    -   Uses Decompose Components (`RootComponent`, `TaskListComponent`, `TaskEditComponent`).
    -   Exposes `StateFlow` or `Value` for the UI to observe.
    -   Handles navigation logic.

-   **UI Layer** (`shared/src/commonMain/.../ui`):
    -   Pure Compose Multiplatform code.
    -   Renders the state provided by the Presentation layer.
    -   100% shared between Android and iOS.

## 🚀 Getting Started

### Prerequisites
*   **JDK 17** or higher.
*   **Android Studio** (Koala or newer recommended).
*   **Kotlin Multiplatform Mobile Plugin**.
*   (Optional) **Xcode** for running the iOS app locally.

### Building the Project

1.  **Clone the repository**:
    ```bash
    git clone <repo-url>
    ```

2.  **Open in Android Studio**.

3.  **Run Android App**:
    -   Select the `androidApp` configuration.
    -   Click the **Run** button (green arrow).

4.  **Run iOS App**:
    -   Open `iosApp/iosApp.xcodeproj` in Xcode (if available).
    -   Run on a simulator.
    -   *Note: Without a Mac, you can verify the iOS build via the provided CI/CD workflow.*

## ✅ Verification

The application includes a thorough verification checklist ensuring:
-   Data persistence works offline.
-   Navigation stack state is preserved.
-   UI renders consistently across platforms.
