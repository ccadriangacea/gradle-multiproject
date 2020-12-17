plugins {
    `maven-publish`
}

val projectVersion: String by System.getProperties()

group = "de.codecentric"
version = projectVersion