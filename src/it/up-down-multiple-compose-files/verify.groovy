import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")

String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()
String composeFileOverride = Paths.get("${basedir}/docker-compose.override.yml").toString()

assert buildLog.contains("Running: docker-compose -f $composeFile -f $composeFileOverride up -d --no-color" as CharSequence)
assert buildLog.contains("Running: docker-compose -f $composeFile -f $composeFileOverride down" as CharSequence)

def cleanUpProcess = new ProcessBuilder("docker", "system", "prune", "-a", "-f").start().waitFor()

assert cleanUpProcess == 0


