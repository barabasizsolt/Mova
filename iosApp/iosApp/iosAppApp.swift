import SwiftUI
import shared

@main
struct iosAppApp: App {
    init() {
        KoinKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
