import java.io.FileInputStream
import java.util.Properties

plugins {
    id("gwangsan.android.core")
    id("gwangsan.android.hilt")
}

android {
    namespace = "com.school_of_company.firebase"

    buildFeatures {
        buildConfig = true
        defaultConfig {
            buildConfigField("String", "FIREBASE_CRASHLYTICS_ISSUE_URL",  getApiKey("FIREBASE_CRASHLYTICS_ISSUE_URL"))
            buildConfigField("String", "DISCORD_WEBHOOK_URL", getApiKey("DISCORD_WEBHOOK_URL"))
            buildConfigField("String", "DISCORD_MEMBER_ID", getApiKey("DISCORD_MEMBER_ID"))
        }
    }
}

dependencies {
    implementation(project(":core:network"))
    implementation(libs.firebase.crashlytics)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
    ksp(libs.retrofit.moshi.codegen)
}

fun getApiKey(propertyKey: String): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey)
        ?: throw IllegalArgumentException("Property $propertyKey not found in local.properties")
}