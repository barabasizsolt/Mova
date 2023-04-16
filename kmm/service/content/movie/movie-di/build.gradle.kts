@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)

}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.koin.core)


    implementation(libs.kotlinx.serialization)

    implementation(project(":kmm:service:network:network-api"))
    implementation(project(":kmm:service:content:movie:movie-api"))
    implementation(project(":kmm:service:content:movie:movie-implementation"))
}