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
    ":phone:core:datastore",
    ":phone:repository:implementations",
    ":phone:repository:interfaces",
    ":phone:core:application",
    ":phone:service:interfaces",
    ":phone:model",
    ":wear:app",
    ":wear:core:application",
    ":wear:core:navigation",
    ":wear:core:resource",
    ":wear:features:waiting",
    ":wear:features:traveling",
    ":wear:features:settings",
    ":wear:repository:interfaces",
    ":wear:repository:implementations",
    ":wear:model",
    ":wear:service",
    ":common:model",
    ":common:resource",
)
