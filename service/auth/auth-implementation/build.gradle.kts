@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.service.auth.implementation"
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.halcyon.oauthStorage)
    implementation(libs.androidx.security)

    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.bundles.test.android)
}