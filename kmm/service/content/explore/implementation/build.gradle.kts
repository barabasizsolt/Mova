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
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization)
                implementation(libs.bundles.ktor.common)

                implementation(project(":kmm:service:content:model:all"))
                implementation(project(":kmm:service:content:pager"))
                implementation(project(":kmm:service:network:api"))
                implementation(project(":kmm:service:content:explore:api"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.explore.implementation"
}