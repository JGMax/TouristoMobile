import com.android.build.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application") version "7.1.0" apply false
    id("com.android.library") version "7.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
    id("org.jetbrains.kotlin.jvm") version "1.6.21" apply false
}

subprojects {
    project.plugins.applyBaseConfig(project)
}

allprojects {
    repositories {
        google()
        maven { setUrl("https://jitpack.io") }
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

fun BaseExtension.baseConfig() {

    compileSdkVersion(AppConfig.compileSdk)

    defaultConfig.apply {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }

    compileOptions.apply {
        sourceCompatibility = AppConfig.CompileOptions.javaSourceCompatibility
        targetCompatibility = AppConfig.CompileOptions.javaSourceCompatibility
    }

    buildTypes.apply {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "${project.rootDir}/app/proguard-rules.pro"
            )
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = AppConfig.CompileOptions.kotlinJvmTarget
        }
    }

    buildFeatures.apply {
        viewBinding = true
    }

    packagingOptions {
        resources.excludes += "DebugProbesKt.bin"
    }
}

fun PluginContainer.applyBaseConfig(project: Project) {
    whenPluginAdded {
        when (this) {
            is AppPlugin -> {
                project.extensions
                    .getByType<AppExtension>()
                    .apply { baseConfig() }
            }
            is LibraryPlugin -> {
                project.extensions
                    .getByType<LibraryExtension>()
                    .apply { baseConfig() }
            }
        }
    }
}
