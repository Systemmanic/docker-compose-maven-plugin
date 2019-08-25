import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")
String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()

assert buildLog.contains("Running: docker-compose -f $composeFile build --pull" as CharSequence)
assert buildLog.contains("Successfully tagged mpdc-it-build-pull:latest")
