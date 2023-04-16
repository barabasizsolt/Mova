@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.domain.util"
}

dependencies {
    implementation(project(":kmm:service:content:pagination"))

    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines)
}