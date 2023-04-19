@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.service.auth.firebase.api"
}

dependencies {
    implementation(libs.google.firebase.auth)
    implementation(libs.google.firebase.bom)
    implementation(libs.firebase.facebook)
    implementation(libs.kotlinx.coroutines)

    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.bundles.test.android)
}