plugins {
    id("gwangsan.android.application")
    id("gwangsan.android.hilt")
    id("gwangsan.android.firebase")
}

android {
    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }

    namespace = "com.school_of_company.gwangsan"
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:design-system"))
    implementation(project(":core:data"))
    implementation(project(":core:local"))
    implementation(project(":core:device"))
    implementation(project(":core:network"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:firebase"))

    implementation(project(":feature:content"))
    implementation(project(":feature:chat"))
    implementation(project(":feature:inform"))
    implementation(project(":feature:main"))
    implementation(project(":feature:post"))
    implementation(project(":feature:signin"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:profile"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.window.size)
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    implementation(libs.kotlinx.immutable)
}