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

                implementation(project(":kmm:service:content:model:all"))
                implementation(project(":kmm:service:content:pager"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.review.api"
}