buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.github.ben.manes.versions)
    alias(libs.plugins.littlerobots)

    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.cocoapods) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

apply(from = "gradle/config/android-library.gradle")
apply(from = "gradle/config/java-library.gradle")
apply(from = "gradle/config/multiplatform-library.gradle")