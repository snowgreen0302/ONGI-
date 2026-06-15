plugins {
    id("com.android.application")
}

val geminiApiKey: String =
        project.findProperty("GEMINI_API_KEY") as String? ?: ""

val publicApiKey: String =
        project.findProperty("PUBLIC_API_KEY") as String? ?: ""

android {

    namespace = "com.example.ongi"

    compileSdk = 34

    buildTypes {

        release {

            isMinifyEnabled = false

            proguardFiles(

                getDefaultProguardFile(
                    "proguard-android-optimize.txt"),

                "proguard-rules.pro"

            )

        }

    }

    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_1_8

        targetCompatibility =
            JavaVersion.VERSION_1_8

    }

    buildFeatures {

        buildConfig = true

    }
    defaultConfig {

        applicationId = "com.example.ongi"

        minSdk = 24

        targetSdk = 34

        versionCode = 1

        versionName = "1.0"

        testInstrumentationRunner =
                "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
                "String",
                "GEMINI_API_KEY",
                "\"${project.findProperty("GEMINI_API_KEY")}\""
        )

        buildConfigField(
                "String",
                "PUBLIC_API_KEY",
                "\"${project.findProperty("PUBLIC_API_KEY")}\""
        )
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}