String buildLog = new File("${basedir}/build.log").getText("UTF-8")

assert buildLog.contains("Creating mpdc-it-up-down-service-1" as CharSequence)
assert buildLog.contains("Creating mpdc-it-up-down-service-2" as CharSequence)
assert !buildLog.contains("Creating mpdc-it-up-down-service-3" as CharSequence)

def cleanUpProcess = new ProcessBuilder("docker", "system", "prune", "-a", "-f").start().waitFor()

assert cleanUpProcess == 0


