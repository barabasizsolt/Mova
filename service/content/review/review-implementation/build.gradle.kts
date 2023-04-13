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
    implementation(project(":service:content:review:review-api"))
    implementation(project(":service:content:model:review"))
    implementation(project(":service:content:model:category"))
    implementation(project(":service:content:pagination"))
}