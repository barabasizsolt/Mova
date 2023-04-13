@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":service:content:model:movie"))
    implementation(project(":service:content:model:tv"))
    implementation(project(":service:content:model:people"))
    implementation(project(":service:content:model:genre"))
    implementation(project(":service:content:model:video"))
    implementation(project(":service:content:model:review"))
    implementation(project(":service:content:model:cast-crew"))
    implementation(project(":service:content:model:detail"))
    implementation(project(":service:content:pagination"))
}