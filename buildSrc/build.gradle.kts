plugins {
    application

    `kotlin-dsl`

    `java-library`
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()

    gradlePluginPortal()

    // Needed for the kotlin allopen plugin
    maven("https://dl.bintray.com/kotlin/kotlin-dev/")
}

val kotlinPluginVersion: String by System.getProperties()
val benManesVersionsPluginVersion: String by System.getProperties()
val shadowPluginVersion: String by System.getProperties()

dependencies {
    // Kotlin plugin
    "api"("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinPluginVersion")

    // Dependencies update
    "api"("com.github.ben-manes:gradle-versions-plugin:$benManesVersionsPluginVersion")

    // ShadowJar
    "api"("com.github.jengelman.gradle.plugins:shadow:$shadowPluginVersion")
}
