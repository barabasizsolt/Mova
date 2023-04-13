@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.koin.core)

    implementation(project(":service:network:network-api"))
    implementation(project(":service:content:cast-crew:cast-crew-api"))
    implementation(project(":service:content:cast-crew:cast-crew-implementation"))
}