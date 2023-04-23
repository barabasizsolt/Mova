import com.barabasizsolt.activityprovider.di.activityProviderModule
import com.barabasizsolt.mova.domain.di.createDomainModules
import com.barabasizsolt.mova.ui.uiModule
import com.barabasizsolt.network.implementation.createNetworkModule
import org.koin.core.module.Module

actual fun createAppModule(
    host: String,
    apiKey: String,
    isDebugBuild: Boolean
): List<Module> = buildList {
    add(activityProviderModule)
    add(createNetworkModule(host = host, apiKey = apiKey, isDebugBuild = isDebugBuild))
    addAll(createDomainModules())
    addAll(uiModule)
}