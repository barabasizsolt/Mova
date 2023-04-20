import org.koin.core.module.Module

expect fun createAppModule(host: String, apiKey: String, isDebugBuild: Boolean): List<Module>