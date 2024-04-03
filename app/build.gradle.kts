/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.6/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    // checkstyle
    id("io.freefair.lombok") version "8.6"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

// checkstyle {
//     configFile = rootProject.file("./config/checkstyle.xml")
//     isIgnoreFailures = false
//     sourceSets = setOf(project.sourceSets.getByName("main")) // Ensure only the main source set is checked, excluding tests
// }

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)
    implementation("com.opencsv:opencsv:5.9")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

javafx {
    modules("javafx.controls")
}

application {
    // Define the main class for the application.
    mainClass = "org.example.App"
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "org.example.App")
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
