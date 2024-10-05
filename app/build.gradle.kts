plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")

}


android {
    namespace = "com.devx.raju"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devx.raju"
        minSdk = 31
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
    dataBinding {
        enable = true
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
kapt{
    generateStubs = true
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


    implementation("androidx.appcompat:appcompat:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    implementation("androidx.constraintlayout:constraintlayout:1.1.3")


    /* Retrofit using RxJava2, Okhttp, Okhttp logging interceptor, Gson  */
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.4.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.4.0")

    /* Picasso lib for image loading  */
    implementation("com.squareup.picasso:picasso:2.71828")

    /* Android Architecture Component - Room Persistence Lib  */
    implementation("androidx.room:room-runtime:2.5.1")
//    annotationProcessor("androidx.room:room-compiler:2.2.0")
    kapt("androidx.room:room-compiler:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")



//    implementation("android.arch.persistence.room:runtime:1.1.1")
//    kapt("android.arch.persistence.room:compiler:1.1.1")
//    kapt("android.arch.persistence.room:compiler:1.1.1")
//    kapt("android.arch.persistence.room:common:1.1.1")
//    annotationProcessor("android.arch.persistence.room:compiler:1.1.1")
//    androidTestImplementation("android.arch.persistence.room:testing:1.1.1")


    /* Dagger2  */
    implementation("com.google.dagger:dagger-android:2.18")
    implementation("com.google.dagger:dagger-android-support:2.18")
    annotationProcessor("com.google.dagger:dagger-android-processor:2.18")
    annotationProcessor("com.google.dagger:dagger-compiler:2.18")
    kapt("com.google.dagger:dagger-android-processor:2.18")
    kapt("com.google.dagger:dagger-compiler:2.18")

    /* Mokito  */
//    testimplementation("org.mockito:mockito-core:$mockito_version"
//    androidTestimplementation("org.mockito:mockito-android:$mockito_version"
//
//    testImplementation("junit:junit:4.12'
//    androidTestImplementation("androidx.arch.core:core-testing:2.0.0'
//    androidTestImplementation("androidx.test.ext:junit:1.1.1'
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0'
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

    // aac dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    kapt("androidx.lifecycle:lifecycle-compiler:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")


    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    // koin dependencies
//    implementation("io.insert-koin:koin-core:3.2.0")
//    implementation("io.insert-koin:koin-core-viewmodel:3.2.0")

    //for coroutine new
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")

    //workmanager
    implementation("androidx.work:work-runtime:2.9.1")
}