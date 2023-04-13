@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines)

    implementation(project(":service:content:model:movie"))
    implementation(project(":service:content:pagination"))
}