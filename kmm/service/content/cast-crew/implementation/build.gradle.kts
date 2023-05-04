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
                implementation(libs.koin.core)

                api(project(":kmm:service:network:api"))
                api(project(":kmm:service:content:cast-crew:api"))
                implementation(project(":kmm:service:content:model:all"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.cast.crew.implementation"
}