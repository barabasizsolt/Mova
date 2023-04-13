@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":service:network:network-api"))
    implementation(project(":service:content:explore:explore-api"))
    implementation(project(":service:content:explore:explore-implementation"))

    implementation(libs.square.retrofit)
    implementation(libs.koin.core)
}