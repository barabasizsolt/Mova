@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.barabasizsolt.firebase.implementation"
}

dependencies {
    implementation(project(":service:auth:firebase:firebase-api"))
    implementation(project(":service:activityprovider:activityprovider-api"))

    implementation(libs.google.firebase.auth)
    implementation(libs.google.firebase.bom)
    implementation(libs.firebase.facebook)
    implementation(libs.google.services.auth)
    implementation(libs.kotlinx.coroutines)

    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.bundles.test.android)
}