plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "com.feylabs.feat_ui_movie_reviews"
    compileSdk = 33

    defaultConfig {
        minSdk = 25
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(project(":core"))
    implementation(project(":uikit"))
    implementation(project(":shared-dependencies"))
    implementation(project(":feature:movie-genre"))

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Kotlin Coroutines for asynchronous programming
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // Dagger Hilt for dependency injection
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Fragment KTX for simplifying Fragment development
    implementation("androidx.fragment:fragment-ktx:1.5.0")

    // Room Dependencies
    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.room:room-ktx:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")

    // Picasso for loading and caching images
    implementation("com.squareup.picasso:picasso:2.8")

    // Retrofit for making implementation requests
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson converter for Retrofit to handle JSON response data
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // OkHttp for handling HTTP requests and caching
    implementation("com.squareup.okhttp3:okhttp:4.9.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1")

    // Navigation
    val navigation_version = "2.5.0"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation_version")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation_version")
}