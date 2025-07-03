import java.io.FileInputStream
import java.util.Properties

plugins {
    id("gwangsan.android.application")
    id("gwangsan.android.hilt")
    alias(libs.plugins.google.services)
}

android {
    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }

    namespace = "com.school_of_company.gwangsan_android"
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:design-system"))
    implementation(project(":feature:signin"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:profile"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":feature:post"))
    implementation(project(":feature:main"))
    implementation(project(":feature:content"))
    implementation(project(":core:device"))
    implementation(project(":core:local"))
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.window.size)
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)

}
fun getApiKey(propertyKey: String) : String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey) ?: throw IllegalArgumentException("Property $propertyKey not found in local.properties")
}