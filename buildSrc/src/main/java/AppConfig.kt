import org.gradle.api.JavaVersion

object AppConfig {
    const val applicationId = "ru.jteam.touristo"
    const val compileSdk = 31
    const val minSdk = 23
    const val targetSdk = 28
    const val versionCode = 1
    const val versionName = "1.0"

    object CompileOptions {
        val javaSourceCompatibility = JavaVersion.VERSION_1_8
        const val kotlinJvmTarget = "1.8"
    }
}
