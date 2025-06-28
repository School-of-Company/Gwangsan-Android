plugins {
    id("gwangsan.android.feature")
    id("gwangsan.android.hilt")
    id("gwangsan.android.compose")

}

android {
    namespace = "com.school_of_company.post"
}

dependencies {
    implementation(libs.coil.kt)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose.android)
}