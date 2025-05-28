import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import java.nio.file.Files
import java.nio.file.StandardCopyOption

buildscript {
    configurations.all {
        resolutionStrategy {
            force("org.yaml:snakeyaml:1.33")
        }
    }
}

plugins {
    java
    id("org.springframework.boot") version "3.5.0-RC1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "6.6.0"
}

group = "io.github.aa55h"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation(libs.minio)
    implementation(libs.jjwt.api)
    implementation(libs.jjwt.impl)
    implementation(libs.jjwt.jackson)
    implementation(libs.springdoc.openapi.starter)
    testImplementation(libs.h2)
    implementation(libs.spring.boot.starter.data.elasticsearch)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.kafka)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.minio)
    testImplementation(libs.elasticsearch)
    testImplementation(libs.kafka)
    testImplementation(libs.postgresql)
    compileOnly(libs.lombok)
    developmentOnly(libs.spring.boot.devtools)
    developmentOnly(libs.spring.boot.docker.compose)
    runtimeOnly(libs.postgresql.postgresql)
    annotationProcessor(libs.spring.boot.configuration.processor)
    annotationProcessor(libs.projectlombok.lombok)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.kafka.test)
    testImplementation(libs.spring.security.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

val specDirectory = layout.buildDirectory.dir("generated/openapi").get().asFile
val specFile = specDirectory.resolve("spec.yaml")

tasks.register("prepareOpenApiSpec") {
    group = "documentation"
    doLast {
        Files.createDirectories(specDirectory.toPath())
    }
}

tasks.register("generateOpenApiSpec") {
    group = "documentation"
    description = "Generates OpenAPI specification from the application"
    dependsOn("prepareOpenApiSpec")
    doLast {
        uri("http://localhost:8080/v3/api-docs.yaml").toURL().openStream().use {
            Files.copy(it, specFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<GenerateTask>("generateDartClient") {
    group = "openapi tools"
    description = "Generate Dart client from OpenAPI specification"
    dependsOn("generateOpenApiSpec")
    inputSpec = specFile.absolutePath
    outputDir = layout.buildDirectory.dir("meliora-client-api").get().asFile.absolutePath
    generatorName = "dart"
}