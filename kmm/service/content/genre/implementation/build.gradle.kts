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

                implementation(project(":kmm:service:network:api"))
                implementation(project(":kmm:service:content:genre:api"))
                implementation(project(":kmm:service:content:model:all"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.genre.implementation"
}