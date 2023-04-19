@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    android()
    ios()
}

android {
    namespace = "com.barabasizsolt.mova.service.activityprovider.api"
}