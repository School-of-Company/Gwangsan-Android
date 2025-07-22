import java.io.FileInputStream
import java.util.Properties

plugins {
    id("gwangsan.android.feature")
    id("gwangsan.android.hilt")
}

android {
    namespace = "com.school_of_company.chat"

    buildFeatures {
        buildConfig = true
        defaultConfig {
            buildConfigField("String", "BASE_URL",  getApiKey("BASE_URL"))
        }
    }
}

dependencies {
    implementation(libs.coil.kt)
    implementation(libs.androidx.navigation.common.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.swiperefresh)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose.android)
}

fun getApiKey(propertyKey: String): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey)
        ?: throw IllegalArgumentException("Property $propertyKey not found in local.properties")
}