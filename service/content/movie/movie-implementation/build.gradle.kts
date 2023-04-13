@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.square.moshi)
    kapt(libs.square.moshiCodegen)
    implementation(libs.kotlinx.coroutines)

    implementation(project(":service:network:network-api"))
    implementation(project(":service:content:movie:movie-api"))
    implementation(project(":service:content:model:movie"))
    implementation(project(":service:content:pagination"))
}