plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    google()
}

// remember to change in Versions.kt
// it is not possible to reference here as they are compiled after this file is compiled
val kotlinVersion = "1.5.21"
val gradleVersion = "7.0.1"

dependencies {
    implementation("com.android.tools.build:gradle:$gradleVersion")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}