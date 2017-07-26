import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")

String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()

assert buildLog.contains("Running: docker-compose -f $composeFile up --no-color")
assert buildLog.contains("Docker Compose has run successfully")

def cleanUpProcess = new ProcessBuilder("docker-compose", "-f", composeFile, "rm", "--force").start().waitFor()

assert cleanUpProcess == 0
