@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

val release = "release"
android {
    compileSdk = 33
    namespace = "com.barabasizsolt.mova"

    defaultConfig {
        applicationId = "com.barabasizsolt.mova"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create(release) {
            storeFile = file("release.keystore")
            keyAlias = "Mova"
            keyPassword = "android"
            storePassword = "android"
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "API_KEY", "\"93697a6983d40e793bc6b81401c77e1c\"")
            buildConfigField("String", "CLIENT_ID", "\"mova-mobile-client\"")
        }

        release {
            isMinifyEnabled = false
            isShrinkResources = false

            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "API_KEY", "\"93697a6983d40e793bc6b81401c77e1c\"")
            buildConfigField("String", "CLIENT_ID", "\"mova-mobile-client\"")

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName(release)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.composeCompiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":ui:navigation"))
    implementation(project(":ui:theme"))
    implementation(project(":domain:domain-di"))
    implementation(project(":domain:domain-usecase"))
    implementation(project(":service:network:network-di"))
    implementation(project(":service:auth:auth-di"))
    implementation(project(":service:auth:firebase:firebase-api"))
    implementation(project(":service:activityprovider:activityprovider-api"))
    implementation(project(":service:activityprovider:activityprovider-di"))

    // Kotlin
    implementation(libs.kotlinx.coroutines)

    // AndroidX
    implementation(libs.bundles.androidx.core)
    implementation(libs.andoridx.activityCompose)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.androidx.lifecycle)

    implementation(libs.androidx.navigationCompose)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.androidx.preferenceDataStore)
    implementation(libs.androidx.profileInstaller)

    // Google
    implementation(libs.google.android.material)

    // Koin Libraries
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    // Coil Image
    implementation(libs.bundles.coil)

    // Voyager
    implementation(libs.bundles.voyager)

    // State Machine
    implementation(libs.statemachine)

    // Test libraries
    testImplementation(libs.test.junit4)
    testImplementation(libs.bundles.test.junit5)
    testRuntimeOnly(libs.test.junit5.jupiterEngine)
    // Kotlin
    testImplementation(libs.test.kotlin.coroutines)
    // Robolectric
    testImplementation(libs.bundles.test.robolectric)
    testRuntimeOnly(libs.test.junit5.vintageEngine)
    // Other libraries
    testImplementation(libs.test.mockitoKotlin)
    testImplementation(libs.test.jraska.livedata)
    testImplementation(libs.test.square.okhttp.webserver)

    // Android Test libraries
    androidTestImplementation(libs.bundles.test.android)
    // Kotlin
    androidTestImplementation(libs.test.kotlin.coroutines)

    // Beagle
    debugImplementation(libs.beagle.uiDrawer)
    debugImplementation(libs.beagle.okHttp)
    releaseImplementation(libs.beagle.noop)
    releaseImplementation(libs.beagle.okHttpNoop)

    // Leakcanary
    debugImplementation(libs.leakcanary)
}