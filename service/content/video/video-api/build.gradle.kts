@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines)

    implementation(project(":service:content:model:video"))
    implementation(project(":service:content:model:category"))
}