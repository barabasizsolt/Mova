@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines)

    implementation(project(":service:content:model:cast-crew"))
    implementation(project(":service:content:model:category"))
}