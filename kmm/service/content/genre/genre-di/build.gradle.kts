@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.koin.core)

    implementation(project(":kmm:service:network:network-api"))
    implementation(project(":kmm:service:content:genre:genre-api"))
    implementation(project(":kmm:service:content:genre:genre-implementation"))
}