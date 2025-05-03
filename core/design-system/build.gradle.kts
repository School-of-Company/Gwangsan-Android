plugins {
    id("gwangsan.android.core")
    id("gwangsan.android.lint")
    id("gwangsan.android.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.school_of_company.design_system"
}

dependencies {
    implementation(libs.coil.kt)
    implementation(libs.androidx.material.ripple)
    implementation(libs.lottie.compose)
}