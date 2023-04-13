@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.koin.core)
    implementation(libs.square.moshi)
    implementation(libs.halcyon.oauthMoshi)
    implementation(libs.halcyon.oauthStorage)
    implementation(libs.halcyon.oauthDependencies)

    implementation(project(":service:network:network-api"))
}