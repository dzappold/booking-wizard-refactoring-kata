plugins {
    java
    kotlin("jvm") version "1.8.10"
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of("18"))
kotlin.jvmToolchain(18)

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("com.approvaltests:approvaltests:18.5.0")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.5")
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.1")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
