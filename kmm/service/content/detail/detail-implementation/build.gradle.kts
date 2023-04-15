@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {
    implementation(libs.square.retrofit)
    implementation(libs.square.moshi)
    kapt(libs.square.moshiCodegen)
    implementation(libs.kotlinx.coroutines)

    implementation(project(":kmm:service:network:network-api"))
    implementation(project(":kmm:service:content:detail:detail-api"))
    implementation(project(":kmm:service:content:model:detail"))
    implementation(project(":kmm:service:content:model:movie"))
    implementation(project(":kmm:service:content:model:tv"))
    implementation(project(":kmm:service:content:model:video"))
    implementation(project(":kmm:service:content:model:review"))
    implementation(project(":kmm:service:content:model:cast-crew"))
}