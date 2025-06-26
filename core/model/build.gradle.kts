plugins {
    id("gwangsan.jvm.library")
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.runtime.v161)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
    ksp(libs.retrofit.moshi.codegen)
}