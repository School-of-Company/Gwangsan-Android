plugins {
    id("gwangsan.android.feature")
    id("gwangsan.android.hilt")
    id("gwangsan.android.compose")

}

android {
    namespace = "com.school_of_company.content"
}

dependencies {
    implementation(libs.coil.kt)
}