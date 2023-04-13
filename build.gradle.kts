buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.github.ben.manes.versions) apply false
    alias(libs.plugins.littlerobots) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

apply(from = "gradle/config/android-library.gradle")
apply(from = "gradle/config/java-library.gradle")