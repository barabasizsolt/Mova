@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.barabasizsolt.mova.domain.usecase"
}

dependencies {
    implementation(project(":domain:domain-util"))
    implementation(project(":domain:domain-model"))

    implementation(project(":service:content:model:movie"))
    implementation(project(":service:content:model:tv"))
    implementation(project(":service:content:model:people"))
    implementation(project(":service:content:model:video"))
    implementation(project(":service:content:model:review"))
    implementation(project(":service:content:model:cast-crew"))
    implementation(project(":service:content:model:detail"))
    implementation(project(":service:content:model:category"))
    implementation(project(":service:content:pagination"))

    implementation(project(":service:content:explore:explore-api"))
    implementation(project(":service:content:explore:explore-di"))
    implementation(project(":service:content:movie:movie-api"))
    implementation(project(":service:content:movie:movie-di"))
    implementation(project(":service:content:people:people-api"))
    implementation(project(":service:content:people:people-di"))
    implementation(project(":service:content:genre:genre-api"))
    implementation(project(":service:content:genre:genre-di"))
    implementation(project(":service:content:video:video-api"))
    implementation(project(":service:content:video:video-di"))
    implementation(project(":service:content:review:review-api"))
    implementation(project(":service:content:review:review-di"))
    implementation(project(":service:content:cast-crew:cast-crew-api"))
    implementation(project(":service:content:cast-crew:cast-crew-di"))
    implementation(project(":service:content:detail:detail-api"))
    implementation(project(":service:content:detail:detail-di"))
    implementation(project(":service:content:filter:filter-api"))
    implementation(project(":service:content:filter:filter-di"))
    implementation(project(":service:auth:firebase:firebase-api"))
    implementation(project(":service:auth:firebase:firebase-di"))

    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines)
}