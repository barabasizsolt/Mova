@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.service.auth.di"
}

dependencies {
    implementation(project(":kmm:service:auth:auth-implementation"))

    implementation(libs.koin.core)
    implementation(libs.halcyon.oauthStorage)
}