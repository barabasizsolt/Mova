@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.service.auth.firebase.di"
}

dependencies {
    implementation(project(":kmm:service:auth:firebase:firebase-api"))
    implementation(project(":kmm:service:auth:firebase:firebase-implementation"))
    implementation(project(":kmm:service:activityprovider:activityprovider-api"))

    implementation(libs.koin.core)

    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)
    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.bundles.test.android)
}