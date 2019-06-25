import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")

String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()


assert buildLog.contains("Running: docker-compose -f $composeFile build --build-arg far=boor --build-arg foo=bar" as CharSequence)

def cleanUpProcess = new ProcessBuilder("docker", "system", "prune", "-a", "-f").start().waitFor()
assert cleanUpProcess == 0


