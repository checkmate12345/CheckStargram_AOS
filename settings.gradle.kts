pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT) // 프로젝트 레벨에서 우선 적용
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CheckStagram"
include(":app")
