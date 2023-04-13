@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.koin.core)

    implementation(project(":service:network:network-api"))
    implementation(project(":service:content:people:people-api"))
    implementation(project(":service:content:people:people-implementation"))
    implementation(project(":service:content:people:people-implementation"))
    implementation(project(":service:content:pagination"))
}