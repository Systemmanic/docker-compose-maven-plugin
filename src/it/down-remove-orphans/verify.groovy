import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")

String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()

assert buildLog.contains("Running: docker-compose -f $composeFile up -d --no-color" as CharSequence)
assert buildLog.contains("Running: docker-compose -f $composeFile down --remove-orphans" as CharSequence)
assert buildLog.contains("Removing orphans")

def cleanUpProcess = new ProcessBuilder("docker", "system", "prune", "-a", "-f").start().waitFor()

assert cleanUpProcess == 0
