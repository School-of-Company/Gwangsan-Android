import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    id("gwangsan.android.core")
    id("gwangsan.android.hilt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.school_of_company.datastore"
}
androidComponents {
    onVariants(selector().all()) { variant ->
        afterEvaluate {
            val capName = variant.name.capitalize()

            tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("ksp${capName}Kotlin") {
                setSource(
                    tasks.getByName("generate${capName}Proto").outputs
                )
            }
        }
    }
}
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.dataStore.core)
    implementation(libs.protobuf.kotlin.lite)
}
