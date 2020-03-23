import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")
String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()

assert buildLog.contains("Add additionnal parameters")
assert buildLog.contains("Running: docker-compose -f $composeFile up --abort-on-container-exit --no-color" as CharSequence)
