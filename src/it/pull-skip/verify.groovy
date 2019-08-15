String buildLog = new File("${basedir}/build.log").getText("UTF-8")

assert buildLog.contains("Skipping execution" as CharSequence)
assert !buildLog.contains("Downloaded newer image for busybox:latest" as CharSequence)
