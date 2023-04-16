@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)
    implementation(libs.bundles.ktor.common)

    implementation(project(":kmm:service:network:network-api"))
    implementation(project(":kmm:service:content:movie:movie-api"))
    implementation(project(":kmm:service:content:model:movie"))
    implementation(project(":kmm:service:content:pagination"))
}