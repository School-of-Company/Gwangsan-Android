package com.school_of_company.convention

import com.android.build.api.dsl.ApplicationExtension
import com.school_of_company.convention.gwangsan.configureKotlinAndroid
import com.school_of_company.convention.gwangsan.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("gwangsan.android.lint")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                compileSdk = 35

                defaultConfig {
                    applicationId = "com.school_of_company.gwangsan"
                    minSdk = 26
                    targetSdk = 35
                    versionCode = 202509024
                    versionName = "1.2.3"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    vectorDrawables.useSupportLibrary = true
                }

                // Enable Jetpack Compose feature for the project
                buildFeatures.compose = true

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                        isShrinkResources = false
                        isDebuggable = false
                    }
                }

                // Set Compose compiler extension version from the Version Catalog
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
                }

                // Add necessary dependencies using Version Catalog bundles
                dependencies {
                    add("implementation", libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
                    add("implementation", libs.findLibrary("androidx-lifecycle-viewModelCompose").get())
                    add("implementation", libs.findLibrary("androidx-activity-compose").get()) }
            }
        }
    }
}