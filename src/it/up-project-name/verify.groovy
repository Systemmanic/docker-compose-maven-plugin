import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")

String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()

assert buildLog.contains("Running: docker-compose --project FOO -f $composeFile up --no-color" as CharSequence)
assert buildLog.contains("Docker Compose has run successfully" as CharSequence)

def cleanUpProcess = new ProcessBuilder("docker", "system", "prune", "-a", "-f").start().waitFor()

assert cleanUpProcess == 0
