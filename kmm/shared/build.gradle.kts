import org.jetbrains.kotlin.gradle.plugin.mpp.Framework.BitcodeEmbeddingMode.BITCODE
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.cocoapods)
}

val dummy = Attribute.of("dummy", String::class.java)
kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0"
        summary = "Kotlin Multiplatform shared module for Mova app."
        homepage = "https://github.com/barabasizsolt/Mova"
        name = "shared"

        framework {
            baseName = "shared"
            isStatic = true
            transitiveExport = false // This is default.
            embedBitcode(BITCODE)

            export(project(":kmm:service:network:api"))
            export(project(":kmm:service:network:implementation"))
            export(project(":kmm:service:content:movie:api"))
            export(project(":kmm:service:content:movie:implementation"))
            export(project(":kmm:service:content:model:all"))
            export(project(":kmm:service:content:pager"))
            export(project(":kmm:service:content:cast-crew:api"))
            export(project(":kmm:service:content:cast-crew:implementation"))
            export(project(":kmm:service:content:detail:api"))
            export(project(":kmm:service:content:detail:implementation"))
            export(project(":kmm:service:content:explore:api"))
            export(project(":kmm:service:content:explore:implementation"))
            export(project(":kmm:service:content:filter:api"))
            export(project(":kmm:service:content:filter:implementation"))
            export(project(":kmm:service:content:genre:api"))
            export(project(":kmm:service:content:genre:implementation"))
            export(project(":kmm:service:content:people:api"))
            export(project(":kmm:service:content:people:implementation"))
            export(project(":kmm:service:content:review:api"))
            export(project(":kmm:service:content:review:implementation"))
            export(project(":kmm:service:content:video:api"))
            export(project(":kmm:service:content:video:implementation"))

            export(project(":kmm:domain"))

            export(project(":kmm:ui"))
        }

        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                api(project(":kmm:service:network:api"))
                implementation(project(":kmm:service:network:implementation"))
                api(project(":kmm:service:content:movie:api"))
                implementation(project(":kmm:service:content:movie:implementation"))
                implementation(project(":kmm:service:content:model:all"))
                implementation(project(":kmm:service:content:pager"))
                api(project(":kmm:service:content:cast-crew:api"))
                implementation(project(":kmm:service:content:cast-crew:implementation"))
                api(project(":kmm:service:content:detail:api"))
                implementation(project(":kmm:service:content:detail:implementation"))
                api(project(":kmm:service:content:explore:api"))
                implementation(project(":kmm:service:content:explore:implementation"))
                api(project(":kmm:service:content:filter:api"))
                implementation(project(":kmm:service:content:filter:implementation"))
                api(project(":kmm:service:content:genre:api"))
                implementation(project(":kmm:service:content:genre:implementation"))
                api(project(":kmm:service:content:people:api"))
                implementation(project(":kmm:service:content:people:implementation"))
                api(project(":kmm:service:content:review:api"))
                implementation(project(":kmm:service:content:review:implementation"))
                api(project(":kmm:service:content:video:api"))
                implementation(project(":kmm:service:content:video:implementation"))

                implementation(project(":kmm:domain"))

                implementation(project(":kmm:ui"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(project(":kmm:service:activityprovider:activityprovider-api"))
                implementation(project(":kmm:service:activityprovider:activityprovider-implementation"))
                implementation(project(":kmm:service:activityprovider:activityprovider-di"))
            }
        }

        val iosX64Main by getting
        //val iosArm64Main by getting
        //val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            //iosArm64Main.dependsOn(this)
            //iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.shared"
}