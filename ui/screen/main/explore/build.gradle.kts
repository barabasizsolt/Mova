@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.ui.screen.main.explore"

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
    implementation(project(":ui:screen:main:base"))
    implementation(project(":domain:domain-model"))
    implementation(project(":domain:domain-util"))
    implementation(project(":domain:domain-usecase"))
    implementation(project(":service:content:pagination"))
    implementation(project(":service:content:genre:genre-api"))
    implementation(project(":service:content:filter:filter-api"))
    implementation(project(":service:content:model:category"))

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