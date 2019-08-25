import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")
String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()

assert buildLog.contains("Running: docker-compose -f $composeFile -H unix:///var/run/docker.sock up -d --no-color" as CharSequence)
assert buildLog.contains("Running: docker-compose -f $composeFile down" as CharSequence)
