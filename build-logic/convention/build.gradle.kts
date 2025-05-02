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
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "gwangsan.android.application"
            implementationClass = "com.school_of_company.convention.AndoridApplicationConventionPlugin"
        }

        register("androidHilt") {
            id = "gwangsan.android.hilt"
            implementationClass = "com.school_of_company.convention.AndoridHiltConventionPlugin"
        }

        register("androidLint") {
            id = "gwangsan.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }

        register("androidCore") {
            id = "gwangsan.android.core"
            implementationClass = "AndoridCoreConventionPlugin"
        }

        register("androidCompose") {
            id = "gwangsan.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("androidFeautre") {
            id = "gwangsan.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("jvmLibrary") {
            id = "gwangsan.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}