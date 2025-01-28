plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.1"
    id("com.github.node-gradle.node") version "3.6.0"
    id("io.spring.dependency-management") version "1.1.7"
}

node {
    version.set("18.17.0")
    npmVersion.set("9.6.7")
    download.set(true)
}

group = "com.oleksii.surovtsev.portfolio"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
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

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.vaadin:vaadin-spring-boot-starter:24.6.1")
    implementation("com.vaadin:vaadin:24.6.1")
    implementation("com.vaadin:vaadin-core:24.6.1")
    implementation("org.parttio:line-awesome:2.1.0")
    implementation("com.vaadin:vaadin-lumo-theme:24.6.1")

    implementation("com.flowingcode.addons.carousel:carousel-addon:2.1.2")

    implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")

    implementation("com.sendgrid:sendgrid-java:5.0.0-rc.1")

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
