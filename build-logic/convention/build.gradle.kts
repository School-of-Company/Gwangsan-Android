@file:Suppress("DEPRECATION")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.firebase.crashlytics.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "gwangsan.android.application"
            implementationClass = "com.school_of_company.convention.AndroidApplicationConventionPlugin"
        }
        register("androidHilt") {
            id = "gwangsan.android.hilt"
            implementationClass = "com.school_of_company.convention.AndroidHiltConventionPlugin"
        }
        register("androidLint") {
            id = "gwangsan.android.lint"
            implementationClass = "com.school_of_company.convention.AndroidLintConventionPlugin"
        }
        register("androidCore") {
            id = "gwangsan.android.core"
            implementationClass = "com.school_of_company.convention.AndroidCoreConventionPlugin"
        }
        register("androidCompose") {
            id = "gwangsan.android.compose"
            implementationClass = "com.school_of_company.convention.AndroidComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "gwangsan.android.feature"
            implementationClass = "com.school_of_company.convention.AndroidFeatureConventionPlugin"
        }
        register("jvmLibrary") {
            id = "gwangsan.jvm.library"
            implementationClass = "com.school_of_company.convention.JvmLibraryConventionPlugin"
        }
        register("androidFirebase") {
            id = "gwangsan.android.firebase"
            implementationClass = "com.school_of_company.convention.AndroidFirebaseConventionPlugin"
        }
    }
}