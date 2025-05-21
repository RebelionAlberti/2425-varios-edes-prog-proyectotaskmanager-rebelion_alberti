plugins {
    kotlin("jvm") version "1.9.0" // o el que uses
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}