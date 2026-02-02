import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        ComposeView()
            // Removed ignoresSafeArea to prevent overlapping with the notch/status bar
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let root = RootComponentFactory.shared.create()
        return MainViewControllerKt.MainViewController(root: root)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
