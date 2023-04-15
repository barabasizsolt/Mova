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

    implementation(project(":kmm:service:network:network-api"))
    implementation(project(":kmm:service:content:people:people-api"))
    implementation(project(":kmm:service:content:model:people"))
    implementation(project(":kmm:service:content:pagination"))
}