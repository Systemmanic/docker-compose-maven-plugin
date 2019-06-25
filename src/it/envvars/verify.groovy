import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")

String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()


assert buildLog.contains("Running: docker-compose -f $composeFile up" as CharSequence)
assert buildLog.contains("Greetings from the environment.")

def cleanUpProcess = new ProcessBuilder("docker", "system", "prune", "-a", "-f").start().waitFor()
assert cleanUpProcess == 0


