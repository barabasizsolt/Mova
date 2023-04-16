@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    android()
    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.bundles.ktor.common)
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
    namespace = "com.barabasizsolt.network.api"
}