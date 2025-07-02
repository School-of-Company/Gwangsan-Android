plugins {
    id("gwangsan.android.feature")
    id("gwangsan.android.hilt")
}

android {
    namespace = "com.school_of_company.content"
}

dependencies {
    implementation(libs.coil.kt)
}