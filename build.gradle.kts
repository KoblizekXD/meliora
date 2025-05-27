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
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:minio:1.21.0")
    testImplementation("org.testcontainers:elasticsearch")
    testImplementation("org.testcontainers:kafka")
    testImplementation("org.testcontainers:postgresql")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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