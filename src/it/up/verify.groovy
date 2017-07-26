import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")

assert buildLog.contains("Running: docker-compose -f " + Paths.get("${basedir}/docker-compose.yml").toString() + " up --no-color")
assert buildLog.contains("Docker Compose has run successfully")
