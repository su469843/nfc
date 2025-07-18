plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android) // 添加Kotlin插件
    id("androidx.navigation.safeargs.kotlin") // 如果需要导航
    id("com.google.dagger.hilt.android") // Hilt
    id("androidx.room") // Room
}

android {
    namespace = "io.qzz.studyhard.nfc"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.qzz.studyhard.nfc"
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
    buildFeatures {
        viewBinding = true
        compose = true // 启用Compose
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // 根据版本调整
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.kotlinx.coroutines.android)
    // 添加其他必要依赖
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}