pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "DemoTaskApp"

include(":app")
include(":core:database")
include(":core:network")
include(":core:domain")
include(":core:data")
include(":core:navigation")
include(":core:presentation")
include(":feature:home:api")
include(":feature:home:data")
include(":feature:home:presentation")
include(":feature:categories:api")
include(":feature:categories:presentation")
include(":feature:main:api")
include(":feature:main:presentation")
