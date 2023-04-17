@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = false
            transitiveExport = true

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
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
//                api(project(":kmm:service:network:api"))
//                implementation(project(":kmm:service:network:implementation"))
//                api(project(":kmm:service:content:movie:api"))
//                implementation(project(":kmm:service:content:movie:implementation"))
//                implementation(project(":kmm:service:content:model:all"))
//                implementation(project(":kmm:service:content:pager"))
//                api(project(":kmm:service:content:cast-crew:api"))
//                implementation(project(":kmm:service:content:cast-crew:implementation"))
//                api(project(":kmm:service:content:detail:api"))
//                implementation(project(":kmm:service:content:detail:implementation"))
//                api(project(":kmm:service:content:explore:api"))
//                implementation(project(":kmm:service:content:explore:implementation"))
//                api(project(":kmm:service:content:filter:api"))
//                implementation(project(":kmm:service:content:filter:implementation"))
//                api(project(":kmm:service:content:genre:api"))
//                implementation(project(":kmm:service:content:genre:implementation"))
//                api(project(":kmm:service:content:people:api"))
//                implementation(project(":kmm:service:content:people:implementation"))
//                api(project(":kmm:service:content:review:api"))
//                implementation(project(":kmm:service:content:review:implementation"))
//                api(project(":kmm:service:content:video:api"))
//                implementation(project(":kmm:service:content:video:implementation"))

                implementation(project(":kmm:domain"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(project(":kmm:ui"))
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

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

//kotlinInject-compiler = { module = "me.tatarka.inject:kotlin-inject-compiler-ksp", version.ref = "kotlininject" }
//kotlinInject-runtime = { module = "me.tatarka.inject:kotlin-inject-runtime", version.ref = "kotlininject" }
//dependencies {
//    add("kspIosX64", libs.kotlinInject.compiler)
//    add("kspIosArm64", libs.kotlinInject.compiler)
//}

android {
    namespace = "com.barabasizsolt.mova.shared"
}