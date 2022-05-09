object Libs {

    private object Versions {
        const val coroutines = "1.6.1"
        const val coil = "2.0.0-rc03"
        const val compressor = "3.0.1"
        const val viewBinding = "1.5.3"
        const val okHttp = "4.9.3"
        const val retrofit = "2.9.0"
        const val koin = "3.1.6"
        const val leakCanary = "2.9.1"
    }

    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val compressor = "id.zelory:compressor:${Versions.compressor}"
    const val viewBinding =
        "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBinding}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val koin = "io.insert-koin:koin-android:${Versions.koin}"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
}

object View {

    private object Versions {
        const val recyclerView = "1.2.1"
        const val constraintLayout = "2.1.3"
        const val viewPager = "1.0.0"
    }

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val viewPager = "androidx.viewpager2:viewpager2:${Versions.viewPager}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}


object AndroidX {

    private object Versions {
        const val coreKtx = "1.7.0"
        const val appCompat = "1.4.1"
        const val material = "1.5.0"
        const val lifecycleRuntimeKtx = "2.4.1"
        const val lifecycleViewModelKtx = "2.4.1"
        const val lifecycleExtensions = "2.2.0"
    }

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModelKtx}"
    const val lifecycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
}
