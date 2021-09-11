// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(kotlin(Dependencies.Plugins.kotlinGradle, version = Versions.Kotlin.kotlin))
        classpath(Dependencies.Plugins.gradle)
        classpath(Dependencies.Plugins.jetifier)
        classpath(Dependencies.Plugins.navigationSafeArgs)
        classpath(Dependencies.Plugins.hilt)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://www.jitpack.io")
    }

}

tasks.register<Delete>("clean") {
    delete(buildDir)
}