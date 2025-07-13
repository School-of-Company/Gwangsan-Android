plugins {
    id("gwangsan.android.feature")

}

android {
    namespace = "com.school_of_company.post"
}

dependencies {
    implementation(libs.coil.kt)
    implementation(libs.swiperefresh)
    implementation(libs.androidx.navigation.compose.android)
    implementation(libs.androidx.hilt.navigation.compose)
}