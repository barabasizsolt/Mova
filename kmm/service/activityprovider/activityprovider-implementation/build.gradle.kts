@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    android()
    ios()

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":kmm:service:activityprovider:activityprovider-api"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.service.activityprovider.implementation"
}