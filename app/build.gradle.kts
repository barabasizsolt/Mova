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
            buildConfigField("String", "HOST", "\"api.themoviedb.org\"")
            buildConfigField("String", "API_KEY", "\"93697a6983d40e793bc6b81401c77e1c\"")
            buildConfigField("String", "CLIENT_ID", "\"mova-mobile-client\"")
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true

            buildConfigField("String", "HOST", "\"api.themoviedb.org\"")
            buildConfigField("String", "API_KEY", "\"93697a6983d40e793bc6b81401c77e1c\"")
            buildConfigField("String", "CLIENT_ID", "\"mova-mobile-client\"")

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName(release)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(project(":kmm:shared"))
    implementation(project(":kmm:service:auth:api"))
    implementation(project(":kmm:service:activityprovider:activityprovider-api"))
    implementation(project(":kmm:service:activityprovider:activityprovider-di"))

    // AndroidX
    implementation(libs.bundles.androidx.core)
    implementation(libs.andoridx.activityCompose)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.androidx.lifecycle)

    implementation(libs.bundles.androidx.compose)

    // Google
    implementation(libs.google.android.material)

    // Koin Libraries
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    // Beagle
    debugImplementation(libs.beagle.uiDrawer)
    debugImplementation(libs.beagle.okHttp)
    releaseImplementation(libs.beagle.noop)
    releaseImplementation(libs.beagle.okHttpNoop)

    // Leakcanary
    debugImplementation(libs.leakcanary)
}