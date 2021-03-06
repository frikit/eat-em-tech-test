plugins {
    java
    kotlin("jvm") version "1.3.70"
}

group = "org.github"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.3")
    implementation("com.google.code.gson", "gson", "2.8.6")
    implementation("org.litote.kmongo:kmongo:3.12.2")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
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
