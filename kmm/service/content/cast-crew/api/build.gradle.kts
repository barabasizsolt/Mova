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

                implementation(project(":kmm:service:content:model:all"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.cast.crew.api"
}