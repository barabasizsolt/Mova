@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.ui.screen.auth.loginregister"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.composeCompiler.get()
    }
}

dependencies {
    implementation(project(":ui:catalog"))
    implementation(project(":ui:theme"))
    implementation(project(":ui:util"))
    implementation(project(":domain:domain-usecase"))
    implementation(project(":kmm:service:auth:firebase:firebase-api"))

    implementation(libs.bundles.voyager)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    releaseImplementation(libs.beagle.noop)
    debugImplementation(libs.beagle.uiDrawer)

    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.bundles.test.android)
}