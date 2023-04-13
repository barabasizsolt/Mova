@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.service.activityprovider.implementation"
}

dependencies {
    implementation(project(":service:activityprovider:activityprovider-api"))
}