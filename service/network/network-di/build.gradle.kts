@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.square.retrofit)
    implementation(libs.square.moshi)
    implementation(libs.halcyon.oauthMoshi)
    implementation(libs.halcyon.oauthStorage)

    implementation(project(":service:network:network-api"))
    implementation(project(":service:network:network-implementation"))
}