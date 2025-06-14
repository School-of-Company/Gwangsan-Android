plugins {
    id("gwangsan.android.feature")

}

android {
    namespace = "com.school_of_company.post"
}

dependencies {
    implementation(libs.coil.kt)
    implementation(libs.androidx.navigation.common.android)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose.android)

}