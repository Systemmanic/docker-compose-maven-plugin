import java.nio.file.Paths

String buildLog = new File("${basedir}/build.log").getText("UTF-8")
String portMappings = new File("${basedir}/target/docker-compose-mappings.properties").getText("UTF-8")
String composeFile = Paths.get("${basedir}/docker-compose.yml").toString()

assert buildLog.contains("Running: docker-compose -f $composeFile ps" as CharSequence)
assert buildLog.contains("Wrote port mappings to ${basedir}/target/docker-compose-mappings.properties" as CharSequence)

assert portMappings.contains("MPDC_IT_PS_A_TCP_666=" as CharSequence)
assert portMappings.contains("MPDC_IT_PS_B_TCP_814=" as CharSequence)
assert portMappings.contains("MPDC_IT_PS_B_TCP_292=" as CharSequence)

def cleanUpProcess = new ProcessBuilder("docker", "system", "prune", "-a", "-f").start().waitFor()

assert cleanUpProcess == 0
