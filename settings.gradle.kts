pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/halcyonmobile/*")
            credentials {
                username = extra.properties["GITHUB_USERNAME"] as String? ?: System.getenv("GITHUB_USERNAME")
                password = extra.properties["GITHUB_TOKEN"] as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
rootProject.name = "Mova"
include(":app")

include(":kmm")

include(":kmm:service")
include(":kmm:service:content")

include(":kmm:service:auth")
include(":kmm:service:auth:firebase")
include(":kmm:service:auth:firebase:firebase-api")
include(":kmm:service:auth:firebase:firebase-di")
include(":kmm:service:auth:firebase:firebase-implementation")

include(":kmm:service:activityprovider")
include(":kmm:service:activityprovider:activityprovider-api")
include(":kmm:service:activityprovider:activityprovider-api")
include(":kmm:service:activityprovider:activityprovider-implementation")
include(":kmm:service:activityprovider:activityprovider-di")

include(":kmm:service:network")
include(":kmm:service:network:api")
include(":kmm:service:network:implementation")
include(":kmm:service:content:movie")
include(":kmm:service:content:movie:api")
include(":kmm:service:content:movie:implementation")
include(":kmm:service:content:model")
include(":kmm:service:content:model:all")
include(":kmm:service:content:pager")
include(":kmm:service:content:cast-crew")
include(":kmm:service:content:cast-crew:api")
include(":kmm:service:content:cast-crew:implementation")
include(":kmm:service:content:detail")
include(":kmm:service:content:detail:api")
include(":kmm:service:content:detail:implementation")
include(":kmm:service:content:explore")
include(":kmm:service:content:explore:api")
include(":kmm:service:content:explore:implementation")
include(":kmm:service:content:filter")
include(":kmm:service:content:filter:api")
include(":kmm:service:content:filter:implementation")
include(":kmm:service:content:genre")
include(":kmm:service:content:genre:api")
include(":kmm:service:content:genre:implementation")
include(":kmm:service:content:people")
include(":kmm:service:content:people:api")
include(":kmm:service:content:people:implementation")
include(":kmm:service:content:review")
include(":kmm:service:content:review:api")
include(":kmm:service:content:review:implementation")
include(":kmm:service:content:video")
include(":kmm:service:content:video:api")
include(":kmm:service:content:video:implementation")

include(":kmm:domain")

include(":kmm:shared")
