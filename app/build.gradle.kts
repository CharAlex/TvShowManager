plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("com.apollographql.apollo").version(Versions.ThirdParty.apollo)
}

android {
    defaultConfig {
        compileSdk = Versions.Android.compileSdk
        applicationId = Config.Application.applicationId
        minSdk = Versions.Android.minSdk
        buildToolsVersion = Versions.Android.buildToolsVersion
        targetSdk = Versions.Android.targetSdk
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    // This is required by one of our libraries (Coil) in order to get compiled correctly.
    compileOptions {
        sourceCompatibility = Config.Application.javaVersion
        targetCompatibility = Config.Application.javaVersion
    }

    kotlinOptions {
        jvmTarget = Config.Application.javaVersionLiteral
    }
}

apollo {
    generateKotlinModels.set(true)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(Dependencies.Kotlin.reflect)
    implementation(Dependencies.Kotlin.coroutines)
    implementation(Dependencies.Kotlin.coroutinesAndroid)
    implementation(Dependencies.Kotlin.standardLib)

    // Android ktx
    implementation( Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.corektx)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.swipeRefreshLayout)
    implementation(Dependencies.AndroidX.navigationFragmentKtx)
    implementation(Dependencies.AndroidX.navigationUIKtx)
    implementation(Dependencies.AndroidX.viewModel)
    implementation(Dependencies.AndroidX.liveData)
    implementation(Dependencies.AndroidX.viewModelSavedState)
    implementation(Dependencies.AndroidX.lifecycles)
    implementation(Dependencies.AndroidX.liveDataReactiveStreams)

    // Google
    implementation(Dependencies.Google.material)
    implementation(Dependencies.Google.hilt)
    implementation(Dependencies.Google.hiltViewModel)
    kapt(Dependencies.Google.hiltCompiler)
    kapt(Dependencies.Google.hiltViewModelCompiler)
    kapt(Dependencies.Google.hiltJetpackCompiler)

    // Room
    implementation(Dependencies.AndroidX.room)
    implementation(Dependencies.AndroidX.roomCoroutines)
    kapt(Dependencies.AndroidX.roomCompiler)

    // ThirdParty
    implementation(Dependencies.ThirdParty.coil)
    implementation(Dependencies.ThirdParty.threetenabp)
    implementation(Dependencies.ThirdParty.timber)

    //Apolo
    implementation(Dependencies.ThirdParty.apollo)
    implementation(Dependencies.ThirdParty.apolloNormalizedCache)
    implementation(Dependencies.ThirdParty.apolloCoroutines)
    implementation(Dependencies.ThirdParty.apolloRuntime)

    //Unit test dependencies
    testImplementation(Dependencies.Test.jUnit)
    testImplementation(Dependencies.Test.jUnitAndroidX)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Test.mockkAndroid)
    testImplementation(Dependencies.Test.coroutines)
    testImplementation(Dependencies.Test.coreAndroidTesting)
    testImplementation(Dependencies.Test.espresso)
    testImplementation(Dependencies.Test.roomTesting)
}
