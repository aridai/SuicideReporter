object Dependencies {

    object Kotlin {

        //  https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib-jdk8
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Project.kotlinVersion}"

        //  https://mvnrepository.com/artifact/org.jetbrains.kotlinx
        object Coroutines {
            private const val version = "1.3.8"

            //  https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"

            //  https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-android
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }
    }

    object AndroidX {

        //  https://mvnrepository.com/artifact/androidx.appcompat/appcompat
        const val appcompat = "androidx.appcompat:appcompat:1.2.0-rc01"

        //  https://mvnrepository.com/artifact/androidx.constraintlayout/constraintlayout
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta8"

        //  https://mvnrepository.com/artifact/androidx.core/core-ktx
        const val coreKtx = "androidx.core:core-ktx:1.3.0"

        //  https://mvnrepository.com/artifact/androidx.fragment/fragment-ktx
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.5"

        //  https://mvnrepository.com/artifact/androidx.lifecycle
        object Lifecycle {
            private const val version = "2.2.0"

            //  https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-extensions
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"

            //  https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-livedata-ktx
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"

            //  https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-viewmodel-ktx
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
    }

    //  https://mvnrepository.com/artifact/com.google.android.material/material
    const val material = "com.google.android.material:material:1.2.0-rc01"

    //  https://mvnrepository.com/artifact/com.jakewharton.timber/timber
    const val timber = "com.jakewharton.timber:timber:4.7.1"
}
