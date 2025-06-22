pluginManagement {
    includeBuild("build-logic")
    repositories {
            google()
            mavenCentral()
            gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "Gwangsan-Android"
include(":app")
include(":core")
include(":core:design-system")
include(":core:common")
include(":core:network")
include(":core:model")
include(":core:datastore")
include(":core:ui")
include(":feature")
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
include(":feature:signup")
include(":feature:signin")
include(":feature:main")
include(":feature:post")
include(":feature:profile")
include(":core:data")
