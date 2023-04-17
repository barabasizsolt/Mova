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
                implementation(libs.kotlinx.coroutines)

                implementation(project(":kmm:service:content:model:all"))
                implementation(project(":kmm:service:content:pager"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.explore.api"
}