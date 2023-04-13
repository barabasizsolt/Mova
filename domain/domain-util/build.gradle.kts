@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":service:content:pagination"))

    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines)
}