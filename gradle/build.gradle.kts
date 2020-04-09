plugins {
    java
    kotlin("jvm") version "1.3.71"
    application
}

group = "bitcoin.difficulty.finder"
version = "2020.alpha.2"

repositories {
    mavenCentral()
}

application {
    mainClassName = "bitcoin.difficulty.finder.playground.MainKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}