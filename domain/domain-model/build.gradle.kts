@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":kmm:service:content:model:movie"))
    implementation(project(":kmm:service:content:model:tv"))
    implementation(project(":kmm:service:content:model:people"))
    implementation(project(":kmm:service:content:model:genre"))
    implementation(project(":kmm:service:content:model:video"))
    implementation(project(":kmm:service:content:model:review"))
    implementation(project(":kmm:service:content:model:cast-crew"))
    implementation(project(":kmm:service:content:model:detail"))
    implementation(project(":kmm:service:content:pagination"))
}