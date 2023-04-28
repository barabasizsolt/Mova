@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.services)
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
                implementation(project(":kmm:service:auth:api"))

                implementation(libs.google.firebase.kmm)
                //implementation(libs.google.firebase.bom)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.koin.core)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(project(":kmm:service:activityprovider:activityprovider-api"))

                implementation(libs.firebase.facebook)
                implementation(libs.google.services.auth)
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.auth.implementation"
}