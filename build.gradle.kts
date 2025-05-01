@file:Suppress("DEPRECATION")

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

allprojects {
    tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinCompile::class.java).configureEach {
        kotlinOptions {
            if (project.findProperty("enableMultiModuleComposeReports") == "true") {
                freeCompilerArgs += listOf("-P", "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + rootProject.buildDir.absolutePath + "/compose_metrics/")
                freeCompilerArgs += listOf("-P", "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + rootProject.buildDir.absolutePath + "/compose_metrics/")
            }
        }
    }
}