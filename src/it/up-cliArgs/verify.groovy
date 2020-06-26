import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")
String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()

assert buildLog.contains("Running: docker-compose -f $composeFile --tlsverify up --no-color" as CharSequence)
