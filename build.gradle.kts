import pl.allegro.tech.build.axion.release.domain.hooks.HookContext
import pl.allegro.tech.build.axion.release.domain.properties.TagProperties
import pl.allegro.tech.build.axion.release.domain.scm.ScmPosition

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.1"
    id("com.github.node-gradle.node") version "3.6.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.vaadin") version "24.5.10"
    id("pl.allegro.tech.build.axion-release") version "1.18.16"
    id("com.gorylenko.gradle-git-properties") version "2.4.2"
}


vaadin {
    productionMode = true
}

node {
    version.set("18.17.0")
    npmVersion.set("9.6.7")
    download.set(true)
}

group = "com.oleksii.surovtsev.portfolio"
version = scmVersion.version


gitProperties {
    failOnNoGitDirectory = false
    keys = mutableListOf(
        "git.commit.id",
        "git.commit.time",
        "git.branch",
        "git.build.version",
        "git.commit.message.full",
        "git.commit.user.name",
        "git.commit.id.abbrev"
    )
}

scmVersion {
    useHighestVersion.set(true)
    branchVersionIncrementer.set(
        mapOf(
            "develop.*" to "incrementPatch",
            "release.*" to "incrementMinor",
            "hotfix.*" to listOf("incrementPrerelease", mapOf("initialPreReleaseIfNotOnPrerelease" to "fx.1")),
        )
    )

    branchVersionCreator.set(
        mapOf(
            "master" to KotlinClosure2({ v: String, s: ScmPosition -> "${v}" }),
            ".*" to KotlinClosure2({ v: String, s: ScmPosition -> "$v-${s.branch}" }),
        )
    )

    snapshotCreator.set { versionFromTag: String, scmPosition: ScmPosition -> "-${scmPosition.shortRevision}" }
    tag.initialVersion.set { tagProperties: TagProperties, _: ScmPosition -> "4.0.0" }
    tag.prefix.set("v")
    tag.versionSeparator.set("")

    // release hook for debugging purposes, please do not remove  
    hooks {
        post { hookContext: HookContext ->
            println("scmVersion previousVersion: ${hookContext.previousVersion}")
            println("scmVersion  releaseVersion: ${hookContext.releaseVersion}")
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "Vaadin Directory"
        url = uri("https://maven.vaadin.com/vaadin-addons")
    }
}



dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.6")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.vaadin:vaadin-spring-boot-starter:24.5.10")
    implementation("com.vaadin:vaadin:24.5.10")
    implementation("com.vaadin:vaadin-core:24.5.10")
    implementation("com.vaadin:vaadin-lumo-theme:24.5.10")

    implementation("org.parttio:line-awesome:2.1.0")

    implementation("com.flowingcode.addons.carousel:carousel-addon:2.1.2")

    implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")

    implementation("com.sendgrid:sendgrid-java:5.0.0-rc.1")

    implementation("net.sf.jasperreports:jasperreports:7.0.1")
    implementation("net.sf.jasperreports:jasperreports-fonts:7.0.1")
    implementation("net.sf.jasperreports:jasperreports-functions:7.0.1")
    implementation("net.sf.jasperreports:jasperreports-pdf:7.0.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
    jvmToolchain(17)
}

tasks.withType<Test> {
    useJUnitPlatform()
}