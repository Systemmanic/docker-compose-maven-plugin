String buildLog = new File("${basedir}/build.log").getText("UTF-8")

assert buildLog.contains("Skipping execution" as CharSequence)
assert !buildLog.contains("Downloaded newer image for busybox:latest")

def cleanUpProcess = new ProcessBuilder("docker", "system", "prune", "-a", "-f").start().waitFor()

assert cleanUpProcess == 0
