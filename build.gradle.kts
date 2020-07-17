import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("com.github.ben-manes.versions") version "0.29.0"
}

buildscript {
    repositories {
        jcenter()
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath(kotlin("gradle-plugin", version = Project.kotlinVersion))
    }
}

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        google()
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    ktlint {
        android.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(true)
        disabledRules.set(listOf("import-ordering", "no-wildcard-imports"))
        reporters { reporter(ReporterType.CHECKSTYLE) }
    }
}

subprojects {
    val isAppModule = (name == "app")

    apply(plugin = if (isAppModule) "com.android.application" else "com.android.library")
    apply(plugin = "org.jetbrains.kotlin.android")
    apply(plugin = "org.jetbrains.kotlin.android.extensions")

    android {
        compileSdkVersion(Project.compileSdk)
        buildToolsVersion = Project.sdkBuildTools

        defaultConfig {
            if (isAppModule) applicationId = Project.applicationId
            minSdkVersion(Project.minSdk)
            targetSdkVersion(Project.targetSdk)
            versionCode = Project.versionCode
            versionName = Project.versionName
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }

        lintOptions {
            xmlReport = true
            isAbortOnError = false
            disable = setOf("ObsoleteLintCustomCheck", "ObsoleteSdkInt")
        }

        testOptions.unitTests.isIncludeAndroidResources = true

        sourceSets {
            main.java.srcDirs("src/main/kotlin")
            test.java.srcDirs("src/test/kotlin")
            androidTest.java.srcDirs("src/androidTest/kotlin")
        }

        buildFeatures.buildConfig = (isAppModule)
        buildFeatures.dataBinding = true

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    kotlinCompile {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
