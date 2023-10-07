plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.feylabs.shared_dependencies"
    compileSdk = 32

    defaultConfig {
        minSdk = 25
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Kotlin Coroutines for asynchronous programming
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // Dagger Hilt for dependency injection
    api("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Fragment KTX for simplifying Fragment development
    api("androidx.fragment:fragment-ktx:1.5.0")

    // Room Dependencies
    api("androidx.room:room-runtime:2.4.2")
    api("androidx.room:room-ktx:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")

    // Picasso for loading and caching images
    api("com.squareup.picasso:picasso:2.8")
    api("com.github.bumptech.glide:glide:4.12.0")

    // Retrofit for making implementation requests
    api("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson converter for Retrofit to handle JSON response data
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    // OkHttp for handling HTTP requests and caching
    api("com.squareup.okhttp3:okhttp:4.9.2")

    api("com.jakewharton.timber:timber:4.7.1")

    // Interceptor
    api("com.squareup.okhttp3:logging-interceptor:4.7.2")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1")

    // Navigation
    val navigation_version = "2.5.0"
    api("androidx.navigation:navigation-fragment-ktx:$navigation_version")
    api("androidx.navigation:navigation-ui-ktx:$navigation_version")

    // CircleIndicator library for ViewPager indicators
    implementation("me.relex:circleindicator:2.1.6")

    // Lottie library for handling and rendering JSON-based animations
    val lottieVersion = "3.6.1"
    implementation("com.airbnb.android:lottie:$lottieVersion")

    // CircleImageView library for circular images
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Glide library for efficient image loading and caching
    implementation("com.github.bumptech.glide:glide:4.12.0")

    // Blurry library for applying blur effects to views or bitmaps
    implementation("jp.wasabeef:blurry:4.0.0")

}