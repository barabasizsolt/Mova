@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.service.activityprovider.di"
}

dependencies {
    implementation(project(":kmm:service:activityprovider:activityprovider-api"))
    implementation(project(":kmm:service:activityprovider:activityprovider-implementation"))

    implementation(libs.koin.core)
}