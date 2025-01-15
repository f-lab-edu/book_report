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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BookReport"
include(":app")
include(":core:data")
include(":core:database")
include(":core:network")
include(":core:designsystem")
include(":core:model")
include(":core:ui")
include(":feature:bookreport")
include(":feature:settings")
include(":feature:favorites")
include(":feature:search")
include(":feature:book")
include(":feature:home")
include(":feature:calendar")
