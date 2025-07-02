plugins {
    id("gwangsan.android.core")
    id("gwangsan.android.hilt")
}

android {
    namespace = "com.school_of_company.device"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}