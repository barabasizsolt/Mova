@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android()
    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization)

                implementation(project(":kmm:service:network:api"))
                implementation(project(":kmm:service:content:pager"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.model.all"
}