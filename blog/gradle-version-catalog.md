# Gradle Version Catalog: Simplifying Dependency Management Between Independent Projects #  

As our projects grow, managing dependencies can become quite a challenge. Ensuring consistency and avoiding version
conflicts across projects are vital for the stability of our builds. This is where Gradle Version Catalogs come into
play. In this article, we will delve into what a Gradle Version Catalog is, why you should use it, how to set it up, and
how to use it in other projects.

## What is a Gradle Version Catalog? ##

Gradle Version Catalog is a feature introduced in Gradle 7.0 that provides a centralized place to declare dependencies
in a project. It helps manage the versions of various dependencies and plugins in a structured and organized manner.
These declarations can be shared and reused across different projects, making it easier to update and manage
dependencies.

## Why Use a Gradle Version Catalog? ##

There are several reasons why using a Gradle Version Catalog can be beneficial:

- Centralized Management: All the dependencies and their versions are kept in one place, making it easier to manage and
  update them.
- Consistency Across Projects: By sharing the same version catalog, all projects can ensure they are using the same
  versions of dependencies, reducing the likelihood of conflicts.
- Improved Readability: With the catalog, the build scripts become more straightforward and more readable as the
  dependencies are referenced by logical aliases rather than their group and artifact IDs.

## How to Set Up a Gradle Version Catalog ##

Gradle Version Catalogs offer a centralized and organized way to manage dependencies and plugins in your project, making
it scalable and easy to maintain. This chapter walks you through the steps to set up a Gradle Version Catalog for your
project.

## Step 1: Create a New Gradle Project ##

First, create a new Gradle project. This project will solely be used as a version catalog.
Example settings.gradle.kts
rootProject.name = "example-gradle-versions-catalog"

## Step 2: Creating the Version Catalog File ##

First and foremost, you need to create a Version Catalog file. This file is conventionally named libs.versions.toml and
should be placed inside the root of your project. The .toml file extension indicates that the file is written in the
TOML (Tom's Obvious, Minimal Language) format, which is easy to read and write.

## Step 3: Structuring the Version Catalog File ##

The `libs.versions.toml` file is structured into four main sections: `versions`, `libraries`, `plugins`, and `bundles`.

1. Versions Section `versions`: This section is used to define version numbers for your dependencies and plugins. By
   defining versions here, you can easily update them in one place when needed.
2. Libraries Section `libraries`: Here, you define your project's dependencies. Instead of specifying the version
   directly,
   you reference the version defined in the `versions` section.
3. Plugins Section `plugins`: Similar to the `libraries` section, this section is for defining your project's plugins,
   referencing the versions from the `versions` section.
4. Bundles Section `bundles`: This section allows you to group several dependencies together. This is useful when
   multiple
   dependencies are used together frequently.

Example structure of `libs.versions.toml`:

```toml
[versions]
fasterxmlJackson = "2.14.1"
kotlin = "1.8.10"
springV2 = "2.7.1"
springV3 = "3.0.6"
snakeyaml = "1.33"

[libraries]
com-fasterxml-jackson-core-jackson-core = { group = "com.fasterxml.jackson.core", name = "jackson-core", version.ref = "fasterxmlJackson" }
com-fasterxml-jackson-core-jackson-databind = { group = "com.fasterxml.jackson.core", name = "jackson-databind", version.ref = "fasterxmlJackson" }
org-postgresql = { group = "org.postgresql", name = "postgresql", version = "42.5.1" }
org-springframework-v2-spring-boot-starter-actuator = { group = "org.springframework.boot", name = "spring-boot-starter-actuator", version.ref = "springV2" }
org-springframework-v2-spring-boot-starter-data-jdbc = { group = "org.springframework.boot", name = "spring-boot-starter-data-jdbc", version.ref = "springV2" }
org-springframework-v2-spring-boot-starter-data-jpa = { group = "org.springframework.boot", name = "spring-boot-starter-data-jpa", version.ref = "springV2" }
org-springframework-v2-spring-boot-starter-test = { group = "org.springframework.boot", name = "spring-boot-starter-test", version.ref = "springV2" }
org-springframework-v2-spring-boot-starter-web = { group = "org.springframework.boot", name = "spring-boot-starter-web", version.ref = "springV2" }

[plugins]
axion-release = { id = "pl.allegro.tech.build.axion-release", version = "1.13.6" }
gradle-git-properties = { id = "com.gorylenko.gradle-git-properties", version = "2.4.1" }
jib = { id = "com.google.cloud.tools.jib", version = "3.3.1" }
kotlin-allopen = { id = "org.jetbrains.kotlin.plugin.allopen", version.ref = "kotlin" }
kotlin-jpa = { id = "org.jetbrains.kotlin.plugin.jpa", version.ref = "kotlin" }
kotlin-jvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin" }
kotlin-spring = { id = "org.jetbrains.kotlin.plugin.spring", version.ref = "kotlin" }
sonarqube = { id = "org.sonarqube", version = "4.0.0.2929" }
spring-boot-v2 = { id = "org.springframework.boot", version.ref = "springV2" }

[bundles]
jackson = ["com-fasterxml-jackson-core-jackson-core", "com-fasterxml-jackson-core-jackson-databind"]
```

## Step 4: Configure Catalog Publishing ##

This step is necessary if you want to publish your version catalog to a remote repository, such as Maven, for sharing
among different projects or teams.
Example `build.gradle.kts`:

```kotlin
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `version-catalog`
    `maven-publish`
}

group = "o.sur.examples"
version = "1.0.0-SNAPSHOT"

catalog {
    versionCatalog {
        from(files("libs.versions.toml"))
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://custom.repository.net/repository/maven-releases/")
            credentials {
                username = repoUsername
                password = repoPassword
            }
        }
    }
    publications {
        create<MavenPublication>("version-catalog") {
            groupId = "$group"
            artifactId = project.name
            version = version
            from(components["versionCatalog"])
        }
    }
}
```

## Step 5: Publish the Version Catalog ##

With everything set up, you can now publish your version catalog to the specified Maven repository by running the
following command:

```shell
./gradlew publish
```

## Step 6: Using the Published Catalog in Other Projects ##

In other projects where you want to use this version catalog, you can add it as a dependency by including the following
in the `settings.gradle.kts` file. For example, add our catalog to the project called kafka-messaging:

```kotlin
rootProject.name = "kafka-messaging"

enableFeaturePreview("VERSION_CATALOGS")

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://custom.repository.net/repository/maven-releases/")
        }
    }
    versionCatalogs {
        create("libs") {
            from("o.sur.examples:example-gradle-version-catalog:1.0.0-SNAPSHOT")
        }
    }
}
```

after setting up settings.gradle.kts of the file, the directory will be available for use in `build.gradle.kts`:

```kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id 'checkstyle'
    jacoco
// Use the plugin `versions` as declared in the `libs` version catalog
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.spring.boot.v2)
}

springBoot { buildInfo() }

group = "com.example.kafka.messaging"
version = 1.0.0-SNAPSHOT
java.sourceCompatibility = JavaVersion.VERSION_17


repositories {
    mavenCentral()
}

// Use the snakeyaml`versions` as declared in the `libs` version catalog
configurations.all {
    resolutionStrategy { force("org.yaml:snakeyaml:${libs.versions.snakeyaml.get()}") }
}

dependencies {
    implementation(libs.org.springframework.v2.spring.boot.starter.actuator)
    implementation(libs.org.springframework.v2.spring.boot.starter.data.jdbc)
    implementation(libs.org.springframework.v2.spring.boot.starter.data.jpa)
    implementation(libs.org.springframework.v2.spring.boot.starter.web)
    implementation(libs.bundles.jackson)
    testImplementation(libs.org.springframework.v2.spring.boot.starter.test)
    testImplementation("org.testcontainers:kafka:1.18.3")
    runtimeOnly(libs.org.postgresql)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> { useJUnitPlatform() }
```

> [!NOTE]Note: If you are using a version of Gradle below 8.1, you need to annotate the plugins{} block with @Suppress("
> DSL_SCOPE_VIOLATION") when using version catalogs. Refer to issue #22797 for more info.

You can find an example of this code in my repository
https://github.com/joleksiysurovtsev/gradle-version-catalog-exampleConclusion:

## Conclusion: ##

The provided example demonstrates how Gradle Version Catalog allows for centralized management of dependencies and
plugins in a project. It provides a structured and organized way to manage versions, making it easier to update and
maintain dependencies.

However, it is important to note that using Gradle Version Catalog doesn't limit you to this approach alone. You can
still directly specify versions of dependencies and plugins if needed for your project. Gradle Version Catalog offers
additional options and flexibility for dependency management.

Another important aspect is the ability to use Gradle Version Catalog in multi-module projects. You can create a shared
version catalog for all modules in the project, ensuring version consistency and simplifying dependency management
across the entire project.

In conclusion, Gradle Version Catalog provides a powerful tool for dependency management, enhancing readability and
consistency across projects. It offers centralization, flexibility, and ease of use, making the development process more
efficient and resilient to version conflicts.

---

## Learn more ##

To learn about additional options for configuring your version catalog, see these resources:
https://docs.gradle.org/current/userguide/platforms.html Sharing dependency versions between projects
The version catalog TOML file format documents additional options for configuring your catalog file.

## Known issues ##

Gradle version catalogs are still under active development. For more info about what isn't yet supported, see the known
issues and limitations.