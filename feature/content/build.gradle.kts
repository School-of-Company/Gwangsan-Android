plugins {
    id("gwangsan.android.feature")
    id("gwangsan.android.hilt")
}

android {
    namespace = "com.school_of_company.content"
}

dependencies {
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.coil.kt)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.swiperefresh)
    implementation(libs.androidx.navigation.compose.android)
}