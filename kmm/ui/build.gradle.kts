@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
}

kotlin {
    android()
    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                implementation(libs.koin.core)
                implementation(libs.google.accompanistSwipeRefresh)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)
                implementation(compose.animation)
                implementation(compose.animationGraphics)

                implementation(project(":kmm:domain"))
                implementation(project(":kmm:service:content:model:all"))
                implementation(project(":kmm:service:content:pager"))
                implementation(project(":kmm:service:content:filter:api"))
                implementation(project(":kmm:service:content:genre:api"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.koin.compose)
                implementation(libs.bundles.androidx.compose)

                implementation(libs.bundles.androidx.core)
                implementation(libs.androidx.appcompat)
                implementation(libs.google.android.material)
                implementation(libs.andoridx.activityCompose)

                implementation(libs.bundles.coil)

                implementation(libs.youtube.player)
                implementation(libs.youtube.player.customui)

                //implementation(libs.beagle.noop)
                //implementation(libs.beagle.uiDrawer)

                implementation(project(":kmm:service:auth:firebase:firebase-api"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.ui"
}