import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")

String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()

assert buildLog.contains("Running: docker-compose -f $composeFile up --no-color" as CharSequence)
assert buildLog.contains("Docker Compose has run successfully with message hello")

evaluate(new File("src/it/prune_docker.groovy"))
