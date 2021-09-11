object Dependencies {

    object Kotlin {
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.Kotlin.kotlin}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutines}"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"
        const val standardLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Kotlin.standardLib}"
    }

    object Plugins {
        const val kotlinGradle = "gradle-plugin"
        const val gradle = "com.android.tools.build:gradle:${Versions.Plugins.gradle}"
        const val jetifier =
            "com.android.tools.build.jetifier:jetifier-processor:${Versions.Plugins.jetifier}"
        const val navigationSafeArgs =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Plugins.navigationSafeArgs}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Plugins.hilt}"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"
        const val corektx = "androidx.core:core-ktx:${Versions.AndroidX.corektx}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX.swipeRefreshLayout}"
        const val navigationFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}"
        const val navigationUIKtx =
            "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.navigation}"
        const val liveDataReactiveStreams =
            "androidx.lifecycle:lifecycle-reactivestreams:${Versions.AndroidX.lifecycleVersion}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycleVersion}"
        const val liveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycleVersion}"
        const val viewModelSavedState =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.AndroidX.lifecycleVersion}"
        const val lifecycles =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycleVersion}"
        const val room = "androidx.room:room-runtime:${Versions.AndroidX.roomVersion}"
        const val roomCoroutines = "androidx.room:room-ktx:${Versions.AndroidX.roomVersion}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.AndroidX.roomVersion}"
    }

    object Google {
        const val hilt =
            "com.google.dagger:hilt-android:${Versions.Google.hilt}"
        const val hiltViewModel =
            "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.Google.hiltViewModel}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Plugins.hilt}"
        const val hiltViewModelCompiler =
            "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.Plugins.hiltViewModel}"
        const val hiltJetpackCompiler =
            "androidx.hilt:hilt-compiler:${Versions.Plugins.hiltViewModel}"

        const val material = "com.google.android.material:material:${Versions.Google.material}"
    }

    object ThirdParty {
        const val coil = "io.coil-kt:coil:${Versions.ThirdParty.coil}"
        const val threetenabp =
            "com.jakewharton.threetenabp:threetenabp:${Versions.ThirdParty.threetenabp}"
        const val timber = "com.jakewharton.timber:timber:${Versions.ThirdParty.timber}"
        const val apollo = "com.apollographql.apollo:apollo-runtime:${Versions.ThirdParty.apollo}"
        const val apolloNormalizedCache = "com.apollographql.apollo:apollo-normalized-cache-sqlite:${Versions.ThirdParty.apollo}"
        const val apolloCoroutines = "com.apollographql.apollo:apollo-coroutines-support:${Versions.ThirdParty.apollo}"
        const val apolloRuntime = "com.apollographql.apollo:apollo-runtime:${Versions.ThirdParty.apollo}"
    }

    object Test {
        const val jUnit = "junit:junit:${Versions.Test.jUnit}"
        const val jUnitAndroidX = "androidx.test.ext:junit:${Versions.Test.jUnitAndroidX}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.Test.espresso}"
        const val roomTesting = "androidx.room:room-testing:${Versions.AndroidX.roomVersion}"
        const val mockk = "io.mockk:mockk:${Versions.Test.mockk}"
        const val mockkAndroid = "io.mockk:mockk-android:${Versions.Test.mockk}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Test.coroutines}"
        const val coreAndroidTesting =
            "androidx.arch.core:core-testing:${Versions.Test.coreAndroidTesting}"

    }
}