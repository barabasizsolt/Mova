@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android()
    ios()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kmm:service:network:api"))

                implementation(libs.bundles.ktor.common)
                implementation(libs.kotlinx.serialization)
                implementation(libs.koin.core)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.bundles.ktor.android)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.bundles.ktor.ios)
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.network.implementation"
}