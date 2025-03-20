plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.checkmate.checkstagram"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.checkmate.checkstagram"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    // 기본 Android 라이브러리
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // 코루틴 & Activity KTX
    implementation(libs.androidx.activity.ktx)
    implementation(libs.kotlinx.coroutines.android)

    // Hilt 의존성
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Room
    implementation(libs.androidx.room.ktx) // Room Kotlin 확장
    implementation(libs.androidx.room.runtime) // Room 런타임 라이브러리
    kapt(libs.androidx.room.compiler) // Room 컴파일러 (kapt 사용)

    // OkHttp
    implementation(libs.okhttp.core) // OkHttp 기본 라이브러리
    implementation(libs.okhttp.logging) // OkHttp 로깅 인터셉터

    // Retrofit & moshi
    implementation(libs.retrofit.core) // Retrofit 기본 라이브러리
    implementation(libs.retrofit.gson) // JSON 변환을 위한 Gson 컨버터
    implementation(libs.moshi)
    implementation(libs.retrofit.moshi)
    kapt(libs.moshi.codegen)

    // Glide
    implementation(libs.glide.core)
    kapt(libs.glide.compiler)

    // ViewPager2
    implementation(libs.androidx.viewpager2)
    implementation(libs.dotsindicator)

    // Splash
    implementation(libs.androidx.core.splashscreen)

    // navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
