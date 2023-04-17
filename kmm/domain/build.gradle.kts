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
                implementation(libs.koin.core)

                implementation(project(":kmm:service:content:model:all"))

                implementation(project(":kmm:service:content:pager"))

                implementation(project(":kmm:service:content:explore:api"))
                implementation(project(":kmm:service:content:explore:implementation"))
                implementation(project(":kmm:service:content:movie:api"))
                implementation(project(":kmm:service:content:movie:implementation"))
                implementation(project(":kmm:service:content:people:api"))
                implementation(project(":kmm:service:content:people:implementation"))
                implementation(project(":kmm:service:content:genre:api"))
                implementation(project(":kmm:service:content:genre:implementation"))
                implementation(project(":kmm:service:content:video:api"))
                implementation(project(":kmm:service:content:video:implementation"))
                implementation(project(":kmm:service:content:review:api"))
                implementation(project(":kmm:service:content:review:implementation"))
                implementation(project(":kmm:service:content:cast-crew:api"))
                implementation(project(":kmm:service:content:cast-crew:implementation"))
                implementation(project(":kmm:service:content:detail:api"))
                implementation(project(":kmm:service:content:detail:implementation"))
                implementation(project(":kmm:service:content:filter:api"))
                implementation(project(":kmm:service:content:filter:implementation"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(project(":kmm:service:auth:firebase:firebase-api"))
                implementation(project(":kmm:service:auth:firebase:firebase-implementation"))
                implementation(project(":kmm:service:auth:firebase:firebase-di"))
            }
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.domain"
}