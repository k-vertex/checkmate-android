plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.checkmate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.checkmate"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.loopj.android:android-async-http:1.4.11")
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
//    implementation("androidx.media3:media3-ui:1.5.0")
//    implementation("com.android.support.design:28.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
//    implementation("com.google.android.exoplayer:exoplayer-core:2.18.7")
//    implementation("com.google.android.exoplayer:exoplayer-ui:2.18.7")
//    implementation("com.google.android.exoplayer:exoplayer-dash:2.18.7")
//    implementation("com.google.android.exoplayer:exoplayer-hls:2.18.7")
//    implementation("com.google.android.exoplayer:exoplayer-rtsp:2.18.7")
//    implementation("com.arthenica:ffmpeg-kit-full:6.0-2")
    // 전체 ExoPlayer 라이브러리 (위의 모든 컴포넌트 포함)
    // implementation "com.google.android.exoplayer:exoplayer:$exoplayer_version"
}