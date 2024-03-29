import com.barabasizsolt.mova.domain.di.createDomainModules
import ui.uiModule
import com.barabasizsolt.network.implementation.createNetworkModule
import org.koin.core.module.Module

actual fun createAppModule(
    host: String,
    apiKey: String,
    isDebugBuild: Boolean
): List<Module> = buildList {
    add(createNetworkModule(host = host, apiKey = apiKey, isDebugBuild = isDebugBuild))
    addAll(createDomainModules())
    addAll(uiModule)
}