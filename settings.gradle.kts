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

rootProject.name = "Maigo Compass"
include(
    ":phone:app",
    ":phone:features:onboarding",
    ":phone:features:ranking",
    ":phone:features:traveling",
    ":phone:features:impressions",
    ":phone:features:share",
    ":phone:core:navigation",
    ":phone:service",
    ":phone:core:resource",
    ":phone:core:util",
    ":phone:repository:implementations",
    ":phone:repository:interfaces",
    ":phone:core:application",
    ":phone:service:interfaces",
    ":wear:app",
)
