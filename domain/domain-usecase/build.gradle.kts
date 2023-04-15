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

    implementation(project(":kmm:service:content:model:movie"))
    implementation(project(":kmm:service:content:model:tv"))
    implementation(project(":kmm:service:content:model:people"))
    implementation(project(":kmm:service:content:model:video"))
    implementation(project(":kmm:service:content:model:review"))
    implementation(project(":kmm:service:content:model:cast-crew"))
    implementation(project(":kmm:service:content:model:detail"))
    implementation(project(":kmm:service:content:model:category"))
    implementation(project(":kmm:service:content:pagination"))

    implementation(project(":kmm:service:content:explore:explore-api"))
    implementation(project(":kmm:service:content:explore:explore-di"))
    implementation(project(":kmm:service:content:movie:movie-api"))
    implementation(project(":kmm:service:content:movie:movie-di"))
    implementation(project(":kmm:service:content:people:people-api"))
    implementation(project(":kmm:service:content:people:people-di"))
    implementation(project(":kmm:service:content:genre:genre-api"))
    implementation(project(":kmm:service:content:genre:genre-di"))
    implementation(project(":kmm:service:content:video:video-api"))
    implementation(project(":kmm:service:content:video:video-di"))
    implementation(project(":kmm:service:content:review:review-api"))
    implementation(project(":kmm:service:content:review:review-di"))
    implementation(project(":kmm:service:content:cast-crew:cast-crew-api"))
    implementation(project(":kmm:service:content:cast-crew:cast-crew-di"))
    implementation(project(":kmm:service:content:detail:detail-api"))
    implementation(project(":kmm:service:content:detail:detail-di"))
    implementation(project(":kmm:service:content:filter:filter-api"))
    implementation(project(":kmm:service:content:filter:filter-di"))
    implementation(project(":kmm:service:auth:firebase:firebase-api"))
    implementation(project(":kmm:service:auth:firebase:firebase-di"))

    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines)
}