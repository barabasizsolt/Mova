import org.jetbrains.kotlin.gradle.plugin.mpp.Framework.BitcodeEmbeddingMode.BITCODE
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.cocoapods)
}

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
        ios.deploymentTarget = "14.1"
        podfile = project.file("../../iosApp/Podfile")

        framework {
            baseName = "shared"
            isStatic = false
            transitiveExport = false
            embedBitcode(BITCODE)
        }

        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
        extraSpecAttributes["exclude_files"] = "['src/commonMain/resources/MR/**']"

        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":kmm:service:network:api"))
                implementation(project(":kmm:service:network:implementation"))
                api(project(":kmm:service:content:movie:api"))
                implementation(project(":kmm:service:content:movie:implementation"))
                implementation(project(":kmm:service:content:model:all"))
                implementation(project(":kmm:service:content:pager"))
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
                api(project(":kmm:service:content:cast-crew:api"))
                implementation(project(":kmm:service:content:cast-crew:implementation"))
                api(project(":kmm:service:auth:api"))
                implementation(project(":kmm:service:auth:implementation"))

                implementation(project(":kmm:domain"))

                implementation(libs.kotlinx.coroutines)
                implementation(libs.koin.core)
                implementation(libs.google.accompanistSwipeRefresh)
                implementation(libs.kmm.imageloader)
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.tab)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)
                implementation(compose.animation)
                implementation(compose.animationGraphics)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.bundles.androidx.compose)
                implementation(libs.koin.compose)

                implementation(libs.bundles.androidx.core)
                implementation(libs.androidx.appcompat)
                implementation(libs.google.android.material)
                implementation(libs.andoridx.activityCompose)

                implementation(libs.bundles.coil)

                implementation(libs.youtube.player)
                implementation(libs.youtube.player.customui)

                //implementation(libs.beagle.noop)
                //implementation(libs.beagle.uiDrawer)

                implementation(project(":kmm:service:activityprovider:activityprovider-api"))
                implementation(project(":kmm:service:activityprovider:activityprovider-implementation"))
                implementation(project(":kmm:service:activityprovider:activityprovider-di"))
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.barabasizsolt.mova.shared"

    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}