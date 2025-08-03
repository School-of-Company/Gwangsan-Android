import java.io.FileInputStream
import java.util.Properties

plugins {
    id("gwangsan.android.core")
    id("gwangsan.android.hilt")
}


android {
    namespace = "com.school_of_company.network"

    buildFeatures {
        buildConfig = true
        defaultConfig {
            buildConfigField("String", "BASE_URL",  getApiKey("BASE_URL"))
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
    ksp(libs.retrofit.moshi.codegen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.socket.io.client)
    implementation(libs.kotlinx.immutable)
}

fun getApiKey(propertyKey: String): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey)
        ?: throw IllegalArgumentException("Property $propertyKey not found in local.properties")
}