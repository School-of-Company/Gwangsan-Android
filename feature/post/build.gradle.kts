plugins {
    id("gwangsan.android.feature")
    id("gwangsan.android.hilt")
    id("gwangsan.android.compose")

}

android {
    namespace = "com.school_of_company.post"
}

dependencies {
    implementation(project(":feature:profile"))
    implementation(libs.coil.kt)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose.android)
    implementation(libs.androidx.exifinterface)
}