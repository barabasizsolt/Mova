@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.square.moshi)
    kapt(libs.square.moshiCodegen)

    implementation(project(":kmm:service:network:network-api"))
    implementation(project(":kmm:service:content:explore:explore-api"))
    implementation(project(":kmm:service:content:model:movie"))
    implementation(project(":kmm:service:content:model:tv"))
    implementation(project(":kmm:service:content:pagination"))
}