import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")

assert buildLog.contains("Running in detached mode")
assert buildLog.contains("Running: docker-compose -f " + Paths.get("${basedir}/docker-compose.yml").toString() + " up -d --no-color")
