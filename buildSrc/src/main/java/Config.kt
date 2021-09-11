import org.gradle.api.JavaVersion

object Config {
    object Application {
        const val applicationId = "com.alexchar.tvshowmanager"
        val javaVersion = JavaVersion.VERSION_1_8
        const val javaVersionLiteral = "1.8"
    }
}