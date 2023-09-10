plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.garib_generalapplicationforruralincomebuilding"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.garib_generalapplicationforruralincomebuilding"
        minSdk = 22
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
    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.1.1")
    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation("com.google.firebase:firebase-firestore:24.7.1")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
//    implementation("androidx.room:room-compiler:2.5.2")
    testImplementation("junit:jic_launcherunit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("com.opencsv:opencsv:5.8")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    implementation("org.tensorflow:tensorflow-lite:2.13.0")
//    implementation("com.google.firebase:firebase-bom:32.2.3")

}