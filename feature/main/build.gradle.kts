plugins {
    id("gwangsan.android.feature")
    id("gwangsan.android.hilt")
    id("gwangsan.android.compose")

}

android {
    namespace = "com.school_of_company.main"
}

dependencies {
    implementation(libs.coil.kt)
    implementation(libs.androidx.navigation.common.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.swiperefresh)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose.android)

}