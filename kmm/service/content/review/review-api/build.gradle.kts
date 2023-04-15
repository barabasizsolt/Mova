@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines)

    implementation(project(":kmm:service:content:model:review"))
    implementation(project(":kmm:service:content:model:category"))
    implementation(project(":kmm:service:content:pagination"))
}