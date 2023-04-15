@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.koin.core)

    implementation(project(":kmm:service:network:network-api"))
    implementation(project(":kmm:service:content:video:video-api"))
    implementation(project(":kmm:service:content:video:video-implementation"))
}