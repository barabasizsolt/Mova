@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.ui.navigation"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.composeCompiler.get()
    }
}

dependencies {
    implementation(project(":ui:screen:auth:welcome"))
    implementation(project(":ui:screen:auth:login-register"))
    implementation(project(":ui:screen:main:base"))
    implementation(project(":ui:screen:main:home"))
    implementation(project(":ui:screen:main:explore"))
    implementation(project(":ui:screen:main:favourites"))
    implementation(project(":ui:screen:main:profile"))
    implementation(project(":ui:screen:main:seeall"))
    implementation(project(":ui:screen:main:detail"))

    implementation(project(":ui:theme"))
    implementation(project(":ui:util"))
    implementation(project(":domain:domain-usecase"))
    implementation(project(":service:content:filter:filter-api"))
    implementation(project(":service:auth:firebase:firebase-api"))

    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.voyager)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.bundles.test.android)
}