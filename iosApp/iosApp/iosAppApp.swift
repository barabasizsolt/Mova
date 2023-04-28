import SwiftUI
import shared
import FirebaseCore

@main
struct iosAppApp: App {
    init() {
        FirebaseApp.configure()
        KoinKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
