@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    //implementation(libs.square.retrofit)
    //implementation(libs.square.moshi)
    //kapt(libs.square.moshiCodegen)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)
    implementation(libs.bundles.ktor.common)

    implementation(project(":kmm:service:network:network-api"))
    //implementation(project(":kmm:service:network:api"))

    implementation(project(":kmm:service:content:movie:movie-api"))
    implementation(project(":kmm:service:content:model:movie"))
    implementation(project(":kmm:service:content:pagination"))
}