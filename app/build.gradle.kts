plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "com.example.openplayassigment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.openplayassigment"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


// ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

// Retrofit for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

// Room for offline caching
    implementation("androidx.room:room-runtime:2.6.0-rc01") // or the latest version
    kapt("androidx.room:room-compiler:2.6.0-rc01")
    annotationProcessor("androidx.room:room-compiler:2.6.0-rc01")
    implementation("androidx.room:room-ktx:2.6.0-rc01")
  //  implementation("androidx.room:room-coroutines:2.5.1")

// Coroutines for background tasks
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")// or the latest version
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2") //

// Paging for handling movie lists efficiently
    implementation("androidx.paging:paging-compose:1.0.0-alpha12")

// Hilt for Dependency Injection
    implementation("com.google.dagger:hilt-android:2.47")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("io.coil-kt:coil-compose:2.4.0")




}