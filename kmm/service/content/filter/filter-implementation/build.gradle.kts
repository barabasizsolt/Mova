@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines)

    implementation(project(":kmm:service:content:filter:filter-api"))
    implementation(project(":kmm:service:content:model:genre"))
    implementation(project(":kmm:service:content:model:category"))
}