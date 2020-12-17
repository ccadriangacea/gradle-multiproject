plugins {
    id("app.kotlin-common-conventions")
}

val vertXVersion: String by System.getProperties()
val nettyVersion: String by System.getProperties()

// This makes sure the version of the dependencies are the ones configured in gradle.properties
configurations.all {
    resolutionStrategy {
        eachDependency {
            when (this@eachDependency.requested.group) {
                "io.vertx" -> {
                    useTarget(mapOf("group" to requested.group, "name" to requested.name, "version" to vertXVersion))
                    because("Vertx version $vertXVersion is latest")
                    logVersionAdjustment(this@eachDependency.requested.toString(), vertXVersion)
                }
                "io.netty" -> {
                    useTarget(mapOf("group" to requested.group, "name" to requested.name, "version" to nettyVersion))
                    because("Netty version $nettyVersion is latest")
                    logVersionAdjustment(this@eachDependency.requested.toString(), nettyVersion)
                }
            }
        }
    }
}

fun logVersionAdjustment(adjustingFor: String, adjustingTo: String) {
    logger.info(" > Adjusting dependency version for[$adjustingFor] to $adjustingTo")
}

dependencies {
    "implementation"("io.vertx:vertx-core:$vertXVersion")
    "implementation"("io.vertx:vertx-lang-kotlin:$vertXVersion")
    "implementation"("io.vertx:vertx-lang-kotlin-coroutines:$vertXVersion")

    // Testing Vert.x
    "testImplementation"("io.vertx:vertx-junit5:$vertXVersion")
}