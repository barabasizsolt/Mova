@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":kmm:service:content:model:movie"))
    implementation(project(":kmm:service:content:model:tv"))
    implementation(project(":kmm:service:content:pagination"))

    implementation(libs.kotlinx.coroutines)
}