@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.square.moshi)
    kapt(libs.square.moshiCodegen)

    implementation(libs.kotlinx.serialization)

    implementation(project(":kmm:service:network:network-api"))
    implementation(project(":kmm:service:content:pagination"))
}