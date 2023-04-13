enableFeaturePreview("VERSION_CATALOGS")

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
                //username = System.getenv("GITHUB_USERNAME")
                //password = System.getenv("GITHUB_TOKEN")
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
include(":service")
include(":service:discover")
include(":service:discover:discover-api")
include(":service:discover:discover-implementation")
include(":service:discover:discover-di")
include(":service:network:network-api")
include(":service:network:network-implementation")
include(":service:network:network-di")
include(":domain")
include(":service:auth")
include(":service:auth:auth-di")
include(":service:auth:auth-implementation")
include(":service:content")
include(":service:content:explore")
include(":service:content:explore:explore-api")
include(":service:content:explore:explore-di")
include(":service:content:explore:explore-implementation")
include(":service:content:model")
include(":service:content:model:model-api")
include(":service:content:model:movie")
include(":service:content:model:people")
include(":service:content:tv")
include(":service:content:model:tv")
include(":service:content:movie")
include(":service:content:movie:movie-api")
include(":service:content:movie:movie-di")
include(":service:content:movie:movie-implementation")
include(":service:content:people")
include(":service:content:people:people-api")
include(":service:content:people:people-di")
include(":service:content:people:people-implementation")
include(":service:content:model:people")
include(":ui")
include(":ui:catalog")
include(":ui:navigation")
include(":ui:theme")
include(":ui:screen")
include(":ui:screen:auth")
include(":ui:screen:auth:login-register")
include(":ui:util")
include(":ui:screen:main")
include(":ui:screen:main:home")
include(":ui:screen:main:explore")
include(":ui:screen:main:profile")
include(":ui:screen:main:favourites")
include(":domain:domain-di")
include(":domain:domain-model")
include(":domain:domain-usecase")
include(":domain:domain-util")
include(":service:auth:firebase")
include(":service:auth:firebase:firebase-api")
include(":service:auth:firebase:firebase-di")
include(":service:auth:firebase:firebase-implementation")
include(":ui:screen:auth:welcome")
include(":service:activityprovider")
include(":service:activityprovider:activityprovider-api")
include(":service:activityprovider:activityprovider-api")
include(":service:activityprovider:activityprovider-implementation")
include(":service:activityprovider:activityprovider-di")
include(":ui:screen:main:seeall")
include(":ui:screen:main:base")
include(":service:content:model:genre")
include(":service:content:genre")
include(":service:content:genre:genre-api")
include(":service:content:genre:genre-implementation")
include(":service:content:genre:genre-di")
include(":service:content:filter")
include(":service:content:filter:filter-api")
include(":service:content:filter:filter-implementation")
include(":service:content:filter:filter-di")
include(":service:content:detail")
include(":service:content:detail:detail-api")
include(":service:content:detail:detail-implementation")
include(":service:content:detail:detail-di")
include(":service:content:model:detail")
include(":service:content:model:video")
include(":service:content:model:review")
include(":service:content:model:cast-crew")
include(":service:content:video")
include(":service:content:video:video-api")
include(":service:content:video:video-implementation")
include(":service:content:video:video-di")
include(":service:content:review")
include(":service:content:review:review-api")
include(":service:content:model:category")
include(":service:content:review:review-implementation")
include(":service:content:review:review-di")
include(":service:content:cast-crew")
include(":service:content:cast-crew:cast-crew-api")
include(":service:content:cast-crew:cast-crew-implementation")
include(":service:content:cast-crew:cast-crew-di")
include(":ui:screen:main:detail")
include(":service:content:pagination")
