plugins {
    // Apply the Spring Boot plugin to add support for building a Spring Boot application in Java.
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Spring Boot dependencies
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation ("org.springframework.boot:spring-boot-starter-websocket")
    implementation ("com.fasterxml.jackson.core:jackson-databind")
    implementation ("mysql:mysql-connector-java")
    // H2 Database for development and testing
    runtimeOnly("com.h2database:h2")

    // Use JUnit Jupiter for testing.
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("org.scpsportfolio.backend.BackendApplication")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
