logger.lifecycle("\n>>> Running settings.gradle in buildSrc\n")

bootstrapProperties()

rootProject.name = "buildSrc"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }

    // Used to overwrite the version for kotlin-dsl present in gradle
    resolutionStrategy {
        val kotlinDslVersion: String by System.getProperties()
        eachPlugin {
            logger.lifecycle(" > Adjusting plugin version for: ${this@eachPlugin.requested.id.id}")
            when (this@eachPlugin.requested.id.id) {
                "org.gradle.kotlin.kotlin-dsl" -> useVersion(kotlinDslVersion)
                    .also { logger.lifecycle("   < Loading plugin: ${this@eachPlugin.requested.id} with version $kotlinDslVersion...") }
            }
        }
    }
}

/*
 * This makes the gradle.properties file available to buildSrc
 */
fun bootstrapProperties() {
    val rootProjectPath = rootDir.parentFile.absolutePath
    logger.lifecycle("  > bootstrapping gradle.properties files from root project: $rootProjectPath")
    org.gradle.util.GUtil.loadProperties(file("$rootProjectPath/gradle.properties"))
        .apply {
            filter { it.key.toString().startsWith("systemProp.") }
                .forEach {
                    logger.debug("    + adding property: $it")
                    it.key.toString().replace("systemProp.", "")
                        .apply { System.getProperties()[this] = it.value }
                }
        }
    logger.lifecycle("  < done loading properties from root gradle.properties...")
    logger.lifecycle("  > System properties listed only on level info")
        .also {
            val propertyPairs = System.getProperties()
                .map { Pair(it.key.toString(), it.value.toString()) }
                .sortedBy { it.first }
            val longestKey = propertyPairs.map { it.first.length }.max() ?: 0

            propertyPairs.map { logger.info("    - ${it.first.padEnd(longestKey)} -> ${it.second}") }
        }
}