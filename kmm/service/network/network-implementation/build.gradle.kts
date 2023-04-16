@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.koin.core)
    implementation(libs.square.moshi)
    implementation(libs.halcyon.oauthMoshi)
    implementation(libs.halcyon.oauthStorage)
    implementation(libs.halcyon.oauthDependencies)

    implementation(libs.bundles.ktor.common)
    implementation(libs.bundles.ktor.android)
    implementation(libs.kotlinx.serialization)

    implementation(project(":kmm:service:network:network-api"))
}