plugins {
    id("gwangsan.android.core")
    id("gwangsan.android.lint")
    id("gwangsan.android.compose")
}

android {
    namespace = "com.school_of_company.design_system"
}

dependencies {
    implementation(libs.coil.kt)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.wear.compose)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.coil.kt)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.material.ripple)
    implementation(libs.androidx.material.ripple)
}