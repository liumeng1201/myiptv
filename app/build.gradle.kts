import java.io.BufferedReader

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.lizongying.mytv0"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.lizongying.mytv0"
        minSdk = 21
        targetSdk = 35
        versionCode = getVersionCode()
        versionName = getVersionName()
    }

    buildFeatures {
        viewBinding = true
    }

    signingConfigs {
        create("common") {
            storeFile = file("mytv.jks")
            storePassword = "mytv123456"
            keyAlias = "mytv"
            keyPassword = "mytv123456"
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("common")
        }
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("common")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Flag to enable support for the new language APIs
        // For AGP 4.1+
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

fun getTag(): String {
    return try {
        val process = Runtime.getRuntime().exec("git describe --tags --always")
        process.waitFor()
        process.inputStream.bufferedReader().use(BufferedReader::readText).trim().removePrefix("v")
    } catch (_: Exception) {
        ""
    }
}

fun getVersionCode(): Int {
    return 16976138
}

fun getVersionName(): String {
    return "1.4.0"
}

dependencies {
    // For AGP 7.4+
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.media3.ui)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.hls)
    implementation(libs.media3.exoplayer.dash)
    implementation(libs.media3.exoplayer.rtsp)
    implementation(libs.media3.datasource.okhttp)
    implementation(libs.media3.datasource.rtmp)

    implementation(libs.nanohttpd)
    implementation(libs.gua64)
    implementation(libs.zxing)
    implementation(libs.glide)

    implementation(libs.gson)
    implementation(libs.okhttp)

    implementation(libs.core.ktx)
    implementation(libs.coroutines)

    implementation(libs.constraintlayout)
    implementation(libs.appcompat)
    implementation(libs.recyclerview)
    implementation(libs.lifecycle.viewmodel)

    implementation(files("libs/lib-decoder-ffmpeg-release.aar"))
}