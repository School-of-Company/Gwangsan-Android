package com.school_of_company.convention

import com.school_of_company.convention.gwangsan.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("gwangsan.android.lint")
                apply("com.google.devtools.ksp")
            }

            configureKotlinJvm()
        }
    }
}