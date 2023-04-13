@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.koin.core)

    implementation(project(":service:network:network-api"))
    implementation(project(":service:content:detail:detail-api"))
    implementation(project(":service:content:detail:detail-implementation"))
}