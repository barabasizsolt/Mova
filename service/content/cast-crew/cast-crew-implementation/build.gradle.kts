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
    implementation(project(":service:content:cast-crew:cast-crew-api"))
    implementation(project(":service:content:model:cast-crew"))
    implementation(project(":service:content:model:category"))
}