@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
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
                implementation(libs.kotlinx.coroutines)
                implementation(libs.google.firebase.kmm)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.google.firebase.bom)
                implementation(libs.firebase.facebook)
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.auth.api"
}