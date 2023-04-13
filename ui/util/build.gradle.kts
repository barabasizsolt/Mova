@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.ui.util"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.composeCompiler.get()
    }
}

dependencies {
    implementation(libs.bundles.androidx.compose)

    releaseImplementation(libs.beagle.noop)
    debugImplementation(libs.beagle.uiDrawer)

    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.exoPlayer)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.bundles.test.android)
}