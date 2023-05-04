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
                implementation(libs.koin.core)

                implementation(project(":kmm:service:activityprovider:activityprovider-api"))
                implementation(project(":kmm:service:activityprovider:activityprovider-implementation"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.service.activityprovider.di"
}