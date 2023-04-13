@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.ui.catalog"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.composeCompiler.get()
    }
}

dependencies {
    implementation(project(":domain:domain-model"))
    implementation(project(":ui:theme"))
    implementation(project(":ui:util"))
    implementation(project(":service:content:model:movie"))
    implementation(project(":service:content:pagination"))

    implementation(libs.youtube.player)
    implementation(libs.youtube.player.customui)

    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.coil)

    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.bundles.test.android)
}