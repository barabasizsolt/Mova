@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines)

    implementation(project(":service:content:model:review"))
    implementation(project(":service:content:model:category"))
    implementation(project(":service:content:pagination"))
}