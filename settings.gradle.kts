import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

rootProject.name = "gradle-multiproject"

private val pathSeparator: String by System.getProperties()
private val gradlePathSeparator: String by System.getProperties()

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

"samples".loadGradleFileStructure(
    listOf(
        "samples:kotlin-app-vanilla",
        "samples:kotlin-library-vanilla",
        "samples:kotlin-vertx-vanilla"
    )
)

// include subdirectories as projects
fun String.loadGradleFileStructure(subprojects: List<String>) {
    val rootProjectPath = rootDir.absolutePath.toString()
    Paths.get(rootProject.projectDir.absolutePath, this)
        .let { toLoadProjectPath ->
            logger.lifecycle("  > Loading: loadGradleFileStructure for project $toLoadProjectPath")
            Files.walk(toLoadProjectPath, 2)
                .filter { it.toFile().isDirectory }
                .forEach { subprojectPath ->
                    absolutePathToGradlePath(subprojectPath, rootProjectPath)
                        .apply {
                            val projectName = this.removePrefix(":")
                            if (subprojects.contains(projectName)) {
                                logger.debug("    > Subproject path: $subprojectPath")
                                logger.debug("      > Subproject: $projectName")

                                include(projectName)
                                logger.debug("      > Including project: $projectName")

                                findProject(this)?.let { project ->
                                    project.name = projectName.substringAfterLast(":")
                                    project.projectDir = subprojectPath.toFile()
                                    logger.debug("      > Updating project: $projectName")
                                }
                            }
                        }
                }
        }
}

fun absolutePathToGradlePath(absolutePath: Path, removePrefix: String): String = absolutePath
    .toAbsolutePath().toString()
    .removePrefix(removePrefix)
    .replace(pathSeparator, gradlePathSeparator)