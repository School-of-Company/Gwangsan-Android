plugins {
    id("gwangsan.android.application")
    id("gwangsan.android.hilt")
}

android {

    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        buildConfigField (
            "String",
            "BASE_URL",
            "\"https://api.example.com\""
        )
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }

    namespace = "com.school_of_company.gwangsan_android"
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))
    implementation(project(":core:design-system"))

    implementation(project(":feature:signin"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:post"))
    implementation(project(":feature:main"))
    implementation(libs.androidx.material3.window.size.class1.android)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose.jvmstubs)
    implementation(libs.androidx.material3.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)


}